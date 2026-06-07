package io.re2cc.controller;

import io.re2cc.model.Equipment;
import io.re2cc.model.GymSystem;
import io.re2cc.exception.InventoryException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InventoryEditController {
    @FXML private TextField txtName;
    @FXML private TextField txtQuantity;

    private GymSystem gymSystem;
    private Equipment equipment;
    private Runnable onSaveCallback;

    public void setEquipmentSystemAndCallback(Equipment eq, GymSystem system, Runnable callback) {
        this.equipment = eq;
        this.gymSystem = system;
        this.onSaveCallback = callback;

        txtName.setText(eq.getName());
        txtName.setDisable(true);
        txtQuantity.setText(String.valueOf(eq.getQuantity()));
    }

    @FXML
    private void handleSave() {
        String qtyStr = txtQuantity.getText().trim();

        if (qtyStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Quantity is required.");
            alert.showAndWait();
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);
            gymSystem.editEquipment(equipment, equipment.getName(), quantity);
            onSaveCallback.run();
            ((Stage) txtQuantity.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity must be an integer.");
            alert.showAndWait();
        } catch (InventoryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }
}
