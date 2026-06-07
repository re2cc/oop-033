package io.re2cc.controller;

import io.re2cc.model.GymSystem;
import io.re2cc.model.MembershipType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MembershipEditController {
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtDuration;

    private GymSystem gymSystem;
    private MembershipType membershipType;
    private Runnable onSaveCallback;

    public void setMembershipSystemAndCallback(MembershipType mt, GymSystem system, Runnable callback) {
        this.membershipType = mt;
        this.gymSystem = system;
        this.onSaveCallback = callback;

        txtName.setText(mt.getName());
        txtName.setDisable(true); // Don't allow changing plan name to avoid conflict
        txtPrice.setText(String.valueOf(mt.getPrice()));
        txtDuration.setText(String.valueOf(mt.getDurationDays()));
    }

    @FXML
    private void handleSave() {
        String priceStr = txtPrice.getText().trim();
        String durationStr = txtDuration.getText().trim();

        if (priceStr.isEmpty() || durationStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Price and Duration are required.");
            alert.showAndWait();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int duration = Integer.parseInt(durationStr);

            if (price < 0 || duration <= 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Price must be positive and duration must be at least 1 day.");
                alert.showAndWait();
                return;
            }

            gymSystem.editMembershipType(membershipType, price, duration);
            onSaveCallback.run();
            ((Stage) txtPrice.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Price must be a valid number, and duration must be an integer.");
            alert.showAndWait();
        }
    }
}
