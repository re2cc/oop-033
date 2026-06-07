package io.re2cc.controller;

import io.re2cc.model.Client;
import io.re2cc.model.GymSystem;
import io.re2cc.model.MembershipType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientEditController {
    @FXML private TextField txtName;
    @FXML private ChoiceBox<MembershipType> choiceMembership;
    @FXML private CheckBox chkAutoRenew;

    private GymSystem gymSystem;
    private Client client;
    private Runnable onSaveCallback;

    public void setClientSystemAndCallback(Client client, GymSystem system, Runnable callback) {
        this.client = client;
        this.gymSystem = system;
        this.onSaveCallback = callback;

        txtName.setText(client.getName());
        chkAutoRenew.setSelected(client.isAutoRenew());

        choiceMembership.getItems().setAll(system.getMembershipTypes());
        choiceMembership.setValue(client.getMembershipType());
    }

    @FXML
    private void handleSave() {
        String name = txtName.getText().trim();
        MembershipType membership = choiceMembership.getValue();
        if (name.isEmpty() || membership == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Name and Membership are required.");
            alert.showAndWait();
            return;
        }

        boolean membershipChanged = !membership.equals(client.getMembershipType());

        if (membershipChanged) {
            // Require payment
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/re2cc/view/PaymentView.fxml"));
                Parent root = loader.load();
                PaymentProcessor processor = loader.getController();
                processor.setData(client, membership.getPrice());

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Process Payment for Plan Change");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (processor.isSuccess()) {
                    gymSystem.editClient(client, name, membership, chkAutoRenew.isSelected());
                    client.setExpirationDate(gymSystem.getSystemDate().plusDays(membership.getDurationDays()));
                    onSaveCallback.run();
                    ((Stage) txtName.getScene().getWindow()).close();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not open payment dialog: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // No payment required, just edit fields
            gymSystem.editClient(client, name, membership, chkAutoRenew.isSelected());
            onSaveCallback.run();
            ((Stage) txtName.getScene().getWindow()).close();
        }
    }
}
