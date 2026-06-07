package io.re2cc.controller;

import io.re2cc.model.GymSystem;
import io.re2cc.exception.GymException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MembershipAddController {
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtDuration;

    private GymSystem gymSystem;
    private Runnable onSaveCallback;

    public void setSystemAndCallback(GymSystem system, Runnable callback) {
        this.gymSystem = system;
        this.onSaveCallback = callback;
    }

    @FXML
    private void handleSave() {
        String name = txtName.getText().trim();
        String priceStr = txtPrice.getText().trim();
        String durationStr = txtDuration.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty() || durationStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are required.");
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

            gymSystem.addMembershipType(name, price, duration);
            onSaveCallback.run();
            ((Stage) txtName.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Price must be a valid number, and duration must be an integer.");
            alert.showAndWait();
        } catch (GymException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }
}
