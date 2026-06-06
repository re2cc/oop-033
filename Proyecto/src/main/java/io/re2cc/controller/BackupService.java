package io.re2cc.controller;

import io.re2cc.model.GymSystem;
import java.io.File;
import java.io.IOException;

public class BackupService implements Runnable {
    private final GymSystem gymSystem;
    private final String backupPath;

    public BackupService(GymSystem gymSystem, String backupPath) {
        this.gymSystem = gymSystem;
        this.backupPath = backupPath;
    }

    @Override
    public void run() {
        try {
            // Simulated delay
            Thread.sleep(1000);

            File file = new File(backupPath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            gymSystem.saveToFile(backupPath);
            System.out.println("[BackupService] Auto-backup completed successfully at: " + backupPath);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[BackupService] Backup task was interrupted.");
        } catch (IOException e) {
            System.err.println("[BackupService] Failed to create backup: " + e.getMessage());
        }
    }
}
