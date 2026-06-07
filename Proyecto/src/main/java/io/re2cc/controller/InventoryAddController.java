package io.re2cc.controller;

import io.re2cc.model.GymSystem;
import io.re2cc.exception.InventoryException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InventoryAddController {
    @FXML private TextField txtName;
    @FXML private TextField txtQuantity;

    private GymSystem gymSystem;
    private Runnable onSaveCallback;

    public void setSystemAndCallback(GymSystem system, Runnable callback) {
        this.gymSystem = system;
        this.onSaveCallback = callback;
    }

    @FXML
    private void handleSave() {
        String name = txtName.getText().trim();
        String qtyStr = txtQuantity.getText().trim();

        if (name.isEmpty() || qtyStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are required.");
            alert.showAndWait();
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);
            gymSystem.addEquipment(name, quantity);
            onSaveCallback.run();
            ((Stage) txtName.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity must be an integer.");
            alert.showAndWait();
        } catch (InventoryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }
}
