package io.re2cc.controller;

import io.re2cc.exception.InvalidPaymentException;
import io.re2cc.model.Client;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Random;

public class PaymentProcessor {

    @FXML
    private TextField txtClientId;
    @FXML
    private TextField txtAmount;
    @FXML
    private ChoiceBox<String> choiceMethod;
    @FXML
    private TextField txtDetails;
    @FXML
    private Button btnPay;

    private Client client;
    private double amount;
    private boolean success = false;

    @FXML
    public void initialize() {
        choiceMethod.getItems().addAll("Card", "Cash", "Points");
        choiceMethod.setValue("Card");

        choiceMethod.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.equals("Card")) {
                txtDetails.setDisable(false);
                txtDetails.setPromptText("16-DIGIT CARD NUMBER");
                txtDetails.setText("");
            } else if (newVal.equals("Cash")) {
                txtDetails.setDisable(false);
                txtDetails.setPromptText("CASH TENDERED AMOUNT");
                txtDetails.setText("");
            } else if (newVal.equals("Points")) {
                txtDetails.setDisable(true);
                txtDetails.setText("");
                int pointsNeeded = (int) Math.ceil(amount);
                txtDetails.setPromptText(pointsNeeded + " POINTS NEEDED");
            }
        });
    }

    public void setData(Client client, double amount) {
        this.client = client;
        this.amount = amount;
        txtClientId.setText(String.valueOf(client.getId()));
        txtAmount.setText(String.format("%.2f", amount));

        if (choiceMethod.getValue().equals("Points")) {
            int pointsNeeded = (int) Math.ceil(amount);
            txtDetails.setPromptText(pointsNeeded + " POINTS NEEDED");
        }
    }

    public boolean isSuccess() {
        return success;
    }

    @FXML
    private void handlePay() {
        String method = choiceMethod.getValue();
        String details = txtDetails.getText();

        // Disable buttons a textbox
        btnPay.setDisable(true);
        choiceMethod.setDisable(true);
        txtDetails.setDisable(true);

        // Create task to run the paymen in the background
        Task<Void> paymentTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Simulated delay
                Thread.sleep(1500);

                if (method.equals("Card")) {
                    String digits = details.replaceAll("\\s+", "");
                    if (!digits.matches("\\d{16}")) {
                        throw new InvalidPaymentException("Invalid card number. Must be exactly 16 digits.");
                    }
                    Random random = new Random();
                    if (random.nextInt(100) < 25) { // 25% chance of decline
                        throw new InvalidPaymentException("Card transaction declined by the bank.");
                    }

                } else if (method.equals("Cash")) {
                    try {
                        double cashTendered = Double.parseDouble(details.trim());
                        if (cashTendered < amount) {
                            throw new InvalidPaymentException("Insufficient cash. Tendered: $"
                                    + String.format("%.2f", cashTendered) + ", Required: $"
                                    + String.format("%.2f", amount));
                        }
                    } catch (NumberFormatException e) {
                        throw new InvalidPaymentException("Invalid cash format. Please enter a valid decimal number.");
                    }

                } else if (method.equals("Points")) {
                    int pointsNeeded = (int) Math.ceil(amount);
                    if (client.getPoints() < pointsNeeded) {
                        throw new InvalidPaymentException("Insufficient loyalty points. Balance: "
                                + client.getPoints() + ", Required: " + pointsNeeded);
                    }
                    client.deductPoints(pointsNeeded);
                }
                return null;
            }
        };

        paymentTask.setOnSucceeded(event -> {
            success = true;
            Stage stage = (Stage) btnPay.getScene().getWindow();
            stage.close();
        });

        paymentTask.setOnFailed(event -> {
            btnPay.setDisable(false);
            choiceMethod.setDisable(false);
            if (!method.equals("Points")) {
                txtDetails.setDisable(false);
            }

            // Error alert
            Throwable err = paymentTask.getException();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment Failed");
            alert.setHeaderText("Transaction Error");
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        });

        new Thread(paymentTask).start();
    }
}
