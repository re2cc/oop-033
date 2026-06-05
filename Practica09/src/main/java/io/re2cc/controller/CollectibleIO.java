package io.re2cc.controller;

import io.re2cc.model.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectibleIO {
    private static final String DATA_DIR = "data";
    private static final String BACKUP_DIR = "data/backups";

    public CollectibleIO() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            Files.createDirectories(Paths.get(BACKUP_DIR));
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    public List<String> listFiles() {
        List<String> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(DATA_DIR))) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    files.add(entry.getFileName().toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error listing files: " + e.getMessage());
        }
        return files;
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    }

    private void createBackup(String srcFilePath, String backupFileName) {
        Path src = Paths.get(srcFilePath);
        if (Files.exists(src)) {
            Path dest = Paths.get(BACKUP_DIR, backupFileName);
            try {
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Backup created successfully: " + dest.getFileName());
            } catch (IOException e) {
                System.err.println("Error creating backup: " + e.getMessage());
            }
        }
    }

    public void exportToCsv(List<PhysicalCollectible> items, String fileName) throws IOException {
        String filePath = DATA_DIR + "/" + fileName;
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Type,Name,Uva,Uvb,Temp,Hum");
            for (PhysicalCollectible item : items) {
                String type = item.getClass().getSimpleName();
                writer.printf("%s,%s,%.2f,%.2f,%.2f,%.2f\n",
                        type,
                        item.getName(),
                        item.getUvaThreshold(),
                        item.getUvbThreshold(),
                        item.getTemperatureThreshold(),
                        item.getHumidityThreshold());
            }
        }
        String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
        String backupName = baseName + "_backup_" + getTimestamp() + ".csv";
        createBackup(filePath, backupName);
    }

    public List<PhysicalCollectible> importFromCsv(String fileName) throws IOException {
        String filePath = DATA_DIR + "/" + fileName;
        List<PhysicalCollectible> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length < 6)
                    continue;

                String type = parts[0].trim();
                String name = parts[1].trim();
                float uva = Float.parseFloat(parts[2].trim());
                float uvb = Float.parseFloat(parts[3].trim());
                float temp = Float.parseFloat(parts[4].trim());
                float hum = Float.parseFloat(parts[5].trim());

                PhysicalCollectible item = null;
                if (type.equalsIgnoreCase("PvcFigureCollectible") || type.equalsIgnoreCase("PVC")) {
                    item = new PvcFigureCollectible(name);
                } else if (type.equalsIgnoreCase("WoodCollectible") || type.equalsIgnoreCase("Wood")) {
                    item = new WoodCollectible(name);
                } else if (type.equalsIgnoreCase("OilPaintingCollectible") || type.equalsIgnoreCase("Painting")) {
                    item = new OilPaintingCollectible(name);
                }

                if (item != null) {
                    item.setUvaThreshold(uva);
                    item.setUvbThreshold(uvb);
                    item.setTemperatureThreshold(temp);
                    item.setHumidityThreshold(hum);
                    items.add(item);
                }
            }
        }
        return items;
    }

    public void serializeCollectibles(List<PhysicalCollectible> items, String fileName) throws IOException {
        String filePath = DATA_DIR + "/" + fileName;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(items);
        }
        String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
        String backupName = baseName + "_backup_" + getTimestamp() + ".ser";
        createBackup(filePath, backupName);
    }

    // Weird warning thing. Apparently Java doesnt like casting from Object to
    // another class
    @SuppressWarnings("unchecked")
    public List<PhysicalCollectible> deserializeCollectibles(String fileName)
            throws IOException, ClassNotFoundException {
        String filePath = DATA_DIR + "/" + fileName;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<PhysicalCollectible>) ois.readObject();
        }
    }

    public void exportToJson(List<PhysicalCollectible> items, String fileName) throws IOException {
        String filePath = DATA_DIR + "/" + fileName;
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < items.size(); i++) {
            PhysicalCollectible item = items.get(i);
            sb.append("  {\n");
            sb.append("    \"type\": \"").append(item.getClass().getSimpleName()).append("\",\n");
            sb.append("    \"name\": \"").append(item.getName()).append("\",\n");
            sb.append("    \"uvaThreshold\": ").append(item.getUvaThreshold()).append(",\n");
            sb.append("    \"uvbThreshold\": ").append(item.getUvbThreshold()).append(",\n");
            sb.append("    \"temperatureThreshold\": ").append(item.getTemperatureThreshold()).append(",\n");
            sb.append("    \"humidityThreshold\": ").append(item.getHumidityThreshold()).append("\n");
            sb.append("  }");
            if (i < items.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(sb.toString());
        }
        String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
        String backupName = baseName + "_backup_" + getTimestamp() + ".json";
        createBackup(filePath, backupName);
    }
}
