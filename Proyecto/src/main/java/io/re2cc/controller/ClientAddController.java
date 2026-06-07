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

public class ClientAddController {
    @FXML
    private TextField txtName;
    @FXML
    private ChoiceBox<MembershipType> choiceMembership;
    @FXML
    private CheckBox chkAutoRenew;

    private GymSystem gymSystem;
    private Runnable onSaveCallback;

    public void setSystemAndCallback(GymSystem system, Runnable callback) {
        this.gymSystem = system;
        this.onSaveCallback = callback;
        choiceMembership.getItems().setAll(system.getMembershipTypes());
        if (!system.getMembershipTypes().isEmpty()) {
            choiceMembership.setValue(system.getMembershipTypes().get(0));
        }
    }

    @FXML
    private void handlePay() {
        String name = txtName.getText().trim();
        MembershipType membership = choiceMembership.getValue();
        if (name.isEmpty() || membership == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Name and Membership are required.");
            alert.showAndWait();
            return;
        }

        // Generate ID
        int newId = 1001;
        for (Client c : gymSystem.getClients()) {
            if (c.getId() >= newId) {
                newId = c.getId() + 1;
            }
        }

        Client tempClient = new Client(newId, name, membership,
                gymSystem.getSystemDate().plusDays(membership.getDurationDays()), chkAutoRenew.isSelected());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/re2cc/view/PaymentView.fxml"));
            Parent root = loader.load();
            PaymentProcessor processor = loader.getController();
            processor.setData(tempClient, membership.getPrice());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Process Payment");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (processor.isSuccess()) {
                gymSystem.addClient(tempClient);
                onSaveCallback.run();
                ((Stage) txtName.getScene().getWindow()).close();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not open payment dialog: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
