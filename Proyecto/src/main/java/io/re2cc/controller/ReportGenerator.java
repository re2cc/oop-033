package io.re2cc.controller;

import io.re2cc.model.AccessLog;
import io.re2cc.model.Client;
import io.re2cc.model.Equipment;
import io.re2cc.model.GymSystem;
import javafx.application.Platform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

public class ReportGenerator implements Runnable {
    private final GymSystem gymSystem;
    private final String filePath;
    private final Runnable onComplete;
    private final Consumer<Exception> onFailure;

    public ReportGenerator(GymSystem gymSystem, String filePath, Runnable onComplete, Consumer<Exception> onFailure) {
        this.gymSystem = gymSystem;
        this.filePath = filePath;
        this.onComplete = onComplete;
        this.onFailure = onFailure;
    }

    @Override
    public void run() {
        try {
            // Simulated delay
            Thread.sleep(2500);

            StringBuilder sb = new StringBuilder();
            sb.append("GymJFX STATUS REPORT         \n");
            sb.append("Simulated Gym Date: ").append(gymSystem.getSystemDate()).append("\n");
            sb.append("Real Generation Date: ").append(LocalDate.now()).append("\n\n");

            List<Client> clients = gymSystem.getClients();
            long activeCount = clients.stream()
                    .filter(c -> !c.getExpirationDate().isBefore(gymSystem.getSystemDate()))
                    .count();
            long expiredCount = clients.size() - activeCount;

            sb.append("CLIENT STATISTICS\n");
            sb.append("Total Clients: ").append(clients.size()).append("\n");
            sb.append("Active Memberships: ").append(activeCount).append("\n");
            sb.append("Expired Memberships: ").append(expiredCount).append("\n\n");

            double estimatedRevenue = 0;
            for (Client c : clients) {
                if (c.getMembershipType() != null) {
                    estimatedRevenue += c.getMembershipType().getPrice();
                }
            }
            sb.append("FINANCIAL\n");
            sb.append("Estimated Membership Sales: $").append(String.format("%.2f", estimatedRevenue)).append("\n\n");

            sb.append("INVENTORY\n");
            for (Equipment eq : gymSystem.getInventory()) {
                sb.append("- ").append(eq.getName()).append(": ").append(eq.getQuantity()).append(" units\n");
            }
            sb.append("\n");

            sb.append("ACCESS LOGS HISTORY");
            List<AccessLog> logs = gymSystem.getAccessLogs();
            sb.append("Total Access Logs: ").append(logs.size()).append("\n");
            for (AccessLog log : logs) {
                sb.append("[").append(log.getDate()).append("] Client ID: ")
                        .append(log.getClientId()).append(" (")
                        .append(log.getClientName()).append(") - ")
                        .append(log.getType()).append("\n");
            }

            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(sb.toString());
            }

            if (onComplete != null) {
                Platform.runLater(onComplete);
            }
        } catch (Exception e) {
            if (onFailure != null) {
                Platform.runLater(() -> onFailure.accept(e));
            }
        }
    }
}
