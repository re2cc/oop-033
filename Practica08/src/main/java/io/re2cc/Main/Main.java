package io.re2cc.Main;

import io.re2cc.model.*;
import io.re2cc.controller.*;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    static void main() {
        Showcase showcase = new Showcase(30); // Initialize showcase with capacity 30 to store seed data + user input
        ShowcaseManager manager = new ShowcaseManager();

        testData(showcase, manager);

        boolean running = true;
        while (running) {
            IO.println("\n--- MAIN MENU ---");
            IO.println("1. Add collectible (create)");
            IO.println("2. Search collectible (read)");
            IO.println("3. Update thresholds (update)");
            IO.println("4. Delete collectible (delete)");
            IO.println("5. List all collectibles (alphabetical natural order)");
            IO.println("6. List collectibles sorted by custom criteria");
            IO.println("7. Compound stream search (sensitive items)");
            IO.println("8. Bulk remove items exceeding humidity limit");
            IO.println("9. Monitor environment (previous practice)");
            IO.println("10. View unique categories & activity log");
            IO.println("0. Exit");

            String choiceStr = IO.readln("Choose an option: ");
            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr.trim());
            } catch (NumberFormatException e) {
                IO.println("Invalid option. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addNewCollectible(showcase, manager);
                    break;
                case 2:
                    searchForCollectible(showcase);
                    break;
                case 3:
                    updateCollectible(showcase);
                    break;
                case 4:
                    deleteCollectible(showcase);
                    break;
                case 5:
                    listAll(showcase);
                    break;
                case 6:
                    listSorted(showcase);
                    break;
                case 7:
                    compoundSearch(showcase);
                    break;
                case 8:
                    bulkRemove(showcase);
                    break;
                case 9:
                    monitorEnv(showcase, manager);
                    break;
                case 10:
                    viewLogs(showcase);
                    break;
                case 0:
                    IO.println("Exiting. Goodbye!");
                    running = false;
                    break;
                default:
                    IO.println("Option out of bounds. Please select a valid number.");
            }
        }
    }

    private static void testData(Showcase showcase, ShowcaseManager manager) {
        try {
            manager.addCollectible(showcase, new PvcFigureCollectible("Hatsune Miku PVC"));
            manager.addCollectible(showcase, new PvcFigureCollectible("Makise Kurisu"));
            manager.addCollectible(showcase, new PvcFigureCollectible("Evangelion Unit 01"));
            manager.addCollectible(showcase, new PvcFigureCollectible("Tennouji Kotarou PVC"));
            manager.addCollectible(showcase, new PvcFigureCollectible("Echidna"));

            manager.addCollectible(showcase, new WoodCollectible("Wooden Duck"));
            manager.addCollectible(showcase, new WoodCollectible("Vintage Oak chest"));
            manager.addCollectible(showcase, new WoodCollectible("Carved totem"));
            manager.addCollectible(showcase, new WoodCollectible("Cedar mask"));
            manager.addCollectible(showcase, new WoodCollectible("Pine shelf"));

            manager.addCollectible(showcase, new OilPaintingCollectible("Starry Night Replica"));
            manager.addCollectible(showcase, new OilPaintingCollectible("Ocean Waves Landscape"));
            manager.addCollectible(showcase, new OilPaintingCollectible("Abstract Canvas"));
            manager.addCollectible(showcase, new OilPaintingCollectible("Monet Giverny Garden Copy"));
            manager.addCollectible(showcase, new OilPaintingCollectible("Gothic Cathedral oil sketch"));
        } catch (Exception e) {
            IO.println("Seeding error: " + e.getMessage());
        }
    }

    private static void addNewCollectible(Showcase showcase, ShowcaseManager manager) {
        IO.println("\nSelect type of collectible:");
        IO.println("1. PVC Figure");
        IO.println("2. Wood Collectible");
        IO.println("3. Oil Painting");
        String typeStr = IO.readln("Type (1-3): ");
        int type = 0;
        try {
            type = Integer.parseInt(typeStr.trim());
        } catch (NumberFormatException e) {
            IO.println("Invalid type.");
            return;
        }

        String name = IO.readln("Enter name of collectible: ").trim();
        if (name.isEmpty()) {
            IO.println("Name cannot be empty.");
            return;
        }

        PhysicalCollectible collectible;
        switch (type) {
            case 1:
                collectible = new PvcFigureCollectible(name);
                break;
            case 2:
                collectible = new WoodCollectible(name);
                break;
            case 3:
                collectible = new OilPaintingCollectible(name);
                break;
            default:
                IO.println("Invalid type chosen.");
                return;
        }

        try {
            manager.addCollectible(showcase, collectible);
            IO.println("Collectible added successfully: " + name);
        } catch (Exception e) {
            IO.println("Error adding collectible: " + e.getMessage());
        }
    }

    private static void searchForCollectible(Showcase showcase) {
        String name = IO.readln("Enter name to search: ").trim();
        showcase.searchCollectible(name).ifPresentOrElse(
                item -> {
                    IO.println("Found: " + item);
                    if (item instanceof Displayable d) {
                        IO.println("Display details: " + d.getDisplayType() + " (enclosed: "
                                + d.requiresEnclosedCabinet() + ")");
                    }
                    if (item instanceof Valuable v) {
                        IO.println("Estimated value at base price $100: $" + v.estimateValue(100.0));
                    }
                },
                () -> IO.println("Collectible '" + name + "' not found in showcase."));
    }

    private static void updateCollectible(Showcase showcase) {
        String name = IO.readln("Enter name of collectible to update: ").trim();
        Optional<PhysicalCollectible> opt = showcase.searchCollectible(name);
        if (opt.isEmpty()) {
            IO.println("Collectible not found.");
            return;
        }

        String tempStr = IO.readln("Enter new Temperature Threshold (°C): ");
        String humStr = IO.readln("Enter new Humidity Threshold (%): ");
        try {
            float temp = Float.parseFloat(tempStr.trim());
            float hum = Float.parseFloat(humStr.trim());
            if (showcase.updateCollectibleThresholds(name, temp, hum)) {
                IO.println("Thresholds updated successfully.");
            } else {
                IO.println("Failed to update thresholds.");
            }
        } catch (IllegalArgumentException e) {
            IO.println("Update failed: " + e.getMessage());
        }
    }

    private static void deleteCollectible(Showcase showcase) {
        String name = IO.readln("Enter name of collectible to delete: ").trim();
        if (showcase.deleteCollectible(name)) {
            IO.println("Collectible deleted successfully.");
        } else {
            IO.println("Collectible not found.");
        }
    }

    private static void listAll(Showcase showcase) {
        IO.println("\n--- LISTING ALL COLLECTIBLES (Alphabetical natural order via Comparable) ---");
        showcase.getStoredCollectibles().stream()
                .sorted()
                .forEach(IO::println);
    }

    private static void listSorted(Showcase showcase) {
        IO.println("\nSelect sorting criteria (using custom Comparators):");
        IO.println("1. Temperature Threshold (Ascending)");
        IO.println("2. Humidity Threshold (Ascending)");
        String sortChoiceStr = IO.readln("Choice (1-2): ");
        int choice = 0;
        try {
            choice = Integer.parseInt(sortChoiceStr.trim());
        } catch (NumberFormatException e) {
            IO.println("Invalid choice.");
            return;
        }

        List<PhysicalCollectible> sorted;
        if (choice == 1) {
            sorted = showcase.getSortedCollectibles(PhysicalCollectible.BY_TEMPERATURE);
            IO.println("\n--- Sorted by Temperature Threshold (Ascending) ---");
        } else if (choice == 2) {
            sorted = showcase.getSortedCollectibles(PhysicalCollectible.BY_HUMIDITY);
            IO.println("\n--- Sorted by Humidity Threshold (Ascending) ---");
        } else {
            IO.println("Invalid choice.");
            return;
        }
        sorted.forEach(IO::println);
    }

    private static void compoundSearch(Showcase showcase) {
        IO.println("\n--- Compound Search / Sensitive Filter ---");
        String maxTempStr = IO.readln("Enter Max Temperature Threshold limit (exclusive): ");
        String maxHumStr = IO.readln("Enter Max Humidity Threshold limit (exclusive): ");
        try {
            float maxTemp = Float.parseFloat(maxTempStr.trim());
            float maxHum = Float.parseFloat(maxHumStr.trim());
            List<PhysicalCollectible> results = showcase.findSensitiveCollectibles(maxTemp, maxHum);
            IO.println("\nResults matching Temp < " + maxTemp + "°C AND Hum < " + maxHum + "%:");
            if (results.isEmpty()) {
                IO.println("No matching collectibles found.");
            } else {
                results.forEach(IO::println);
            }
        } catch (NumberFormatException e) {
            IO.println("Invalid thresholds entered.");
        }
    }

    private static void bulkRemove(Showcase showcase) {
        IO.println("\n--- Bulk Delete (via Iterator.remove) ---");
        String maxHumStr = IO.readln("Remove all items with humidity threshold exceeding (%): ");
        try {
            float maxHum = Float.parseFloat(maxHumStr.trim());
            showcase.removeCollectiblesExceedingHumidity(maxHum);
            IO.println("Bulk removal process finished.");
        } catch (NumberFormatException e) {
            IO.println("Invalid humidity threshold.");
        }
    }

    private static void monitorEnv(Showcase showcase, ShowcaseManager manager) {
        IO.println("\n--- Environmental Sensor Check ---");
        String sensorId = IO.readln("Enter Environmental Sensor ID (or press Enter for 'Sensor01'): ").trim();
        if (sensorId.isEmpty()) {
            sensorId = "Sensor01";
        }
        manager.monitorEnvironment(showcase, sensorId);
    }

    private static void viewLogs(Showcase showcase) {
        IO.println("\n--- Unique Categories currently in Showcase (HashSet) ---");
        HashSet<String> tags = showcase.getUniqueTags();
        if (tags.isEmpty()) {
            IO.println("No categories recorded.");
        } else {
            IO.println(String.join(", ", tags));
        }

        IO.println("\n--- Activity/Maintenance Log (LinkedList history) ---");
        LinkedList<String> logs = showcase.getMaintenanceHistory();
        if (logs.isEmpty()) {
            IO.println("No activities logged.");
        } else {
            logs.forEach(log -> IO.println("- " + log));
        }
    }
}
