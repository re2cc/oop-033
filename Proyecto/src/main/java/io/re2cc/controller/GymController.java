package io.re2cc.controller;

import io.re2cc.model.*;
import io.re2cc.exception.*;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GymController {

    private static final String DB_FILE = "db.ser";
    private GymSystem gymSystem;

    @FXML private Label lblCurrentDate;
    @FXML private TextField txtAccessClientId;

    @FXML private TableView<Client> tblClients;
    @FXML private TableColumn<Client, Integer> colClientId;
    @FXML private TableColumn<Client, String> colClientName;
    @FXML private TableColumn<Client, String> colClientMembership;
    @FXML private TableColumn<Client, String> colClientExpiration;
    @FXML private TableColumn<Client, Boolean> colClientAutoRenew;
    @FXML private TableColumn<Client, String> colClientAccessStatus;
    @FXML private TableColumn<Client, Integer> colClientPoints;

    @FXML private TableView<MembershipType> tblMemberships;
    @FXML private TableColumn<MembershipType, String> colMembershipType;
    @FXML private TableColumn<MembershipType, Integer> colMembershipDuration;
    @FXML private TableColumn<MembershipType, Double> colMembershipPrice;

    @FXML private TableView<Equipment> tblInventory;
    @FXML private TableColumn<Equipment, String> colInventoryItem;
    @FXML private TableColumn<Equipment, Integer> colInventoryQuantity;

    @FXML private TableView<GymSystem.ClassDateGroup> tblClassDates;
    @FXML private TableColumn<GymSystem.ClassDateGroup, String> colClassDate;
    @FXML private TableColumn<GymSystem.ClassDateGroup, Integer> colClassDateQuantity;

    @FXML private TableView<ClassSession> tblClassDetails;
    @FXML private TableColumn<ClassSession, String> colClassName;

    @FXML
    public void initialize() {
        loadOrCreateSystem();
        setupCellValueFactories();

        // Add class dates listener (required to update the right table)
        tblClassDates.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tblClassDetails.getItems().setAll(gymSystem.getClassSessionsForDate(newVal.getDate()));
            } else {
                tblClassDetails.getItems().clear();
            }
        });

        updateUI();

        // Membership notification
        checkExpiringMemberships();
    }

    private void loadOrCreateSystem() {
        File file = new File(DB_FILE);
        if (file.exists()) {
            try {
                gymSystem = GymSystem.loadFromFile(DB_FILE);
                System.out.println("Loaded database from: " + DB_FILE);
                return;
            } catch (Exception e) {
                System.err.println("Could not load db.ser: " + e.getMessage() + ". Regenerating seed data.");
            }
        }
        gymSystem = new GymSystem();
        gymSystem.seedData();
        saveSystem();
    }

    private void saveSystem() {
        try {
            gymSystem.saveToFile(DB_FILE);
        } catch (IOException e) {
            showError("Save Error", "Could not save database file to " + DB_FILE + ": " + e.getMessage());
        }
    }

    private void setupCellValueFactories() {
        // Clients table
        colClientId.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());
        colClientName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colClientMembership.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getMembershipType() != null ? cell.getValue().getMembershipType().getName() : "None"));
        colClientExpiration.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getExpirationDate() != null ? cell.getValue().getExpirationDate().toString() : ""));
        colClientAutoRenew.setCellValueFactory(cell -> new SimpleBooleanProperty(cell.getValue().isAutoRenew()));
        colClientAccessStatus.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isInside() ? "Inside" : "Outside"));
        colClientPoints.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getPoints()).asObject());

        // Memberships table
        colMembershipType.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colMembershipDuration.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getDurationDays()).asObject());
        colMembershipPrice.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getPrice()).asObject());

        // Inventory table
        colInventoryItem.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colInventoryQuantity.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());

        // Class date groupings table
        colClassDate.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDate().toString()));
        colClassDateQuantity.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());

        // Class details table
        colClassName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
    }

    private void updateUI() {
        lblCurrentDate.setText("DATE: " + gymSystem.getSystemDate());

        tblClients.getItems().setAll(gymSystem.getClients());
        tblMemberships.getItems().setAll(gymSystem.getMembershipTypes());
        tblInventory.getItems().setAll(gymSystem.getInventory());

        // Remember class dates
        GymSystem.ClassDateGroup selectedGroup = tblClassDates.getSelectionModel().getSelectedItem();
        tblClassDates.getItems().setAll(gymSystem.getClassDateGroups());
        if (selectedGroup != null) {
            for (GymSystem.ClassDateGroup g : tblClassDates.getItems()) {
                if (g.getDate().equals(selectedGroup.getDate())) {
                    tblClassDates.getSelectionModel().select(g);
                    break;
                }
            }
        }
    }

    private void checkExpiringMemberships() {
        List<String> expiringClients = new ArrayList<>();
        LocalDate today = gymSystem.getSystemDate();
        for (Client c : gymSystem.getClients()) {
            LocalDate exp = c.getExpirationDate();
            if (!exp.isBefore(today) && !exp.isAfter(today.plusDays(3))) {
                long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, exp);
                expiringClients.add("- " + c.getName() + " (expires in " + daysLeft + " days on " + exp + ")");
            }
        }
        if (!expiringClients.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Membership Expirations");
            alert.setHeaderText("Memberships expiring in 3 days or less:");
            alert.setContentText(String.join("\n", expiringClients));
            alert.showAndWait();
        }
    }

    // Main handlers

    @FXML
    private void handleSave() {
        saveSystem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data successfully serialized to: " + DB_FILE);
        alert.showAndWait();
    }

    @FXML
    private void handleGenerateReport() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Report generation started in a background thread...");
        infoAlert.show();

        ReportGenerator task = new ReportGenerator(
                gymSystem,
                "gym_report.txt",
                () -> {
                    infoAlert.close();
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Report successfully written to gym_report.txt");
                    success.showAndWait();
                },
                ex -> {
                    infoAlert.close();
                    Alert failure = new Alert(Alert.AlertType.ERROR, "Report generation failed: " + ex.getMessage());
                    failure.showAndWait();
                }
        );
        new Thread(task).start();
    }

    @FXML
    private void handleNextDay() {
        List<String> renewalLog = gymSystem.advanceOneDay();
        saveSystem();
        updateUI();

        // auto-backup 
        String backupFile = "backups/db_backup_" + gymSystem.getSystemDate() + ".ser";
        BackupService backup = new BackupService(gymSystem, backupFile);
        new Thread(backup).start();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Date Advanced");
        alert.setHeaderText("Gym Date advanced to: " + gymSystem.getSystemDate());
        if (renewalLog.isEmpty()) {
            alert.setContentText("No membership events today.");
        } else {
            alert.setContentText(String.join("\n", renewalLog));
        }
        alert.showAndWait();

        checkExpiringMemberships();
    }

    @FXML
    private void handleAccessEnter() {
        String idStr = txtAccessClientId.getText().trim();
        if (idStr.isEmpty()) {
            showWarning("Input Required", "Please enter a Client ID.");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            gymSystem.registerAccessEnter(id);
            Client c = gymSystem.findClientById(id);
            saveSystem();
            updateUI();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Access Granted!\nWelcome, " + c.getName() + ".\nLoyalty Points: " + c.getPoints());
            alert.setTitle("Check-In Successful");
            alert.showAndWait();
            txtAccessClientId.clear();
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Client ID must be a number.");
        } catch (Exception e) {
            showError("Access Denied", e.getMessage());
        }
    }

    @FXML
    private void handleAccessExit() {
        String idStr = txtAccessClientId.getText().trim();
        if (idStr.isEmpty()) {
            showWarning("Input Required", "Please enter a Client ID.");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            gymSystem.registerAccessExit(id);
            Client c = gymSystem.findClientById(id);
            saveSystem();
            updateUI();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Goodbye, " + c.getName() + "!");
            alert.setTitle("Check-Out Successful");
            alert.showAndWait();
            txtAccessClientId.clear();
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Client ID must be a number.");
        } catch (Exception e) {
            showError("Exit Failed", e.getMessage());
        }
    }

    @FXML
    private void handleClientAdd() {
        showDialog("/io/re2cc/view/ClientAddView.fxml", "Add Client", ctrl -> {
            ((ClientAddController) ctrl).setSystemAndCallback(gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    @FXML
    private void handleClientEdit() {
        Client selected = tblClients.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select a client to edit.");
            return;
        }
        showDialog("/io/re2cc/view/ClientEditView.fxml", "Edit Client", ctrl -> {
            ((ClientEditController) ctrl).setClientSystemAndCallback(selected, gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    @FXML
    private void handleMembershipAdd() {
        showDialog("/io/re2cc/view/MembershipAddView.fxml", "Add Membership Plan", ctrl -> {
            ((MembershipAddController) ctrl).setSystemAndCallback(gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    @FXML
    private void handleMembershipEdit() {
        MembershipType selected = tblMemberships.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select a membership plan to edit.");
            return;
        }
        showDialog("/io/re2cc/view/MembershipEditView.fxml", "Edit Membership Plan", ctrl -> {
            ((MembershipEditController) ctrl).setMembershipSystemAndCallback(selected, gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    @FXML
    private void handleInventoryAdd() {
        showDialog("/io/re2cc/view/InventoryAddView.fxml", "Add Equipment", ctrl -> {
            ((InventoryAddController) ctrl).setSystemAndCallback(gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    @FXML
    private void handleInventoryEdit() {
        Equipment selected = tblInventory.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select an equipment item to edit.");
            return;
        }
        showDialog("/io/re2cc/view/InventoryEditView.fxml", "Edit Equipment", ctrl -> {
            ((InventoryEditController) ctrl).setEquipmentSystemAndCallback(selected, gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    @FXML
    private void handleClassAdd() {
        showDialog("/io/re2cc/view/ClassAddView.fxml", "Schedule Class", ctrl -> {
            ((ClassAddController) ctrl).setSystemAndCallback(gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    @FXML
    private void handleClassEdit() {
        ClassSession selected = tblClassDetails.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select a class session from the details table to edit.");
            return;
        }
        showDialog("/io/re2cc/view/ClassEditView.fxml", "Reschedule/Rename Class", ctrl -> {
            ((ClassEditController) ctrl).setClassSessionSystemAndCallback(selected, gymSystem, () -> {
                saveSystem();
                updateUI();
            });
        });
    }

    // Show dialog helper
    private void showDialog(String fxmlPath, String title, DialogInitializer initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            initializer.initialize(loader.getController());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            showError("Navigation Error", "Could not open dialog " + title + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface DialogInitializer {
        void initialize(Object controller) throws Exception;
    }

    //  Show warning helper
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
