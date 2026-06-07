package io.re2cc.controller;

import io.re2cc.model.GymSystem;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClassAddController {
    @FXML private DatePicker dpDate;
    @FXML private TextField txtName;

    private GymSystem gymSystem;
    private Runnable onSaveCallback;

    public void setSystemAndCallback(GymSystem system, Runnable callback) {
        this.gymSystem = system;
        this.onSaveCallback = callback;
        dpDate.setValue(system.getSystemDate());
    }

    @FXML
    private void handleSave() {
        LocalDate date = dpDate.getValue();
        String name = txtName.getText().trim();

        if (date == null || name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are required.");
            alert.showAndWait();
            return;
        }

        gymSystem.addClassSession(date, name);
        onSaveCallback.run();
        ((Stage) txtName.getScene().getWindow()).close();
    }
}
