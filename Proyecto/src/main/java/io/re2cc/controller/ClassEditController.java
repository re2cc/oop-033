package io.re2cc.controller;

import io.re2cc.model.ClassSession;
import io.re2cc.model.GymSystem;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClassEditController {
    @FXML private DatePicker dpDate;
    @FXML private TextField txtName;

    private GymSystem gymSystem;
    private ClassSession classSession;
    private Runnable onSaveCallback;

    public void setClassSessionSystemAndCallback(ClassSession cs, GymSystem system, Runnable callback) {
        this.classSession = cs;
        this.gymSystem = system;
        this.onSaveCallback = callback;

        dpDate.setValue(cs.getDate());
        txtName.setText(cs.getName());
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

        gymSystem.editClassSession(classSession, date, name);
        onSaveCallback.run();
        ((Stage) txtName.getScene().getWindow()).close();
    }
}
