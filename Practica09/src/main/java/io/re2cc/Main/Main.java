package io.re2cc.Main;

import io.re2cc.model.*;
import io.re2cc.controller.*;
import java.util.List;

public class Main {
    static void main() {
        Showcase showcase = new Showcase(30);
        ShowcaseManager manager = new ShowcaseManager();
        CollectibleIO fileIO = new CollectibleIO();

        testData(showcase, manager);

        boolean running = true;
        while (running) {
            IO.println("\n--- MAIN MENU ---");
            IO.println("1. Add collectible (in-memory)");
            IO.println("2. List collectibles (in-memory)");
            IO.println("3. Export files (CSV, JSON, Binary + Auto-backup)");
            IO.println("4. Import/Restore files (CSV, Binary)");
            IO.println("5. List saved files in data directory");
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
                    listAll(showcase);
                    break;
                case 3:
                    exportCollectiblesMenu(showcase, fileIO);
                    break;
                case 4:
                    importCollectiblesMenu(showcase, fileIO, manager);
                    break;
                case 5:
                    listSavedFiles(fileIO);
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

    private static void listAll(Showcase showcase) {
        IO.println("\n--- LISTING ALL COLLECTIBLES (Alphabetical natural order via Comparable) ---");
        showcase.getStoredCollectibles().stream()
                .sorted()
                .forEach(IO::println);
    }

    private static void exportCollectiblesMenu(Showcase showcase, CollectibleIO fileIO) {
        IO.println("\nSelect export format:");
        IO.println("1. CSV Format");
        IO.println("2. JSON Format (Decision Propia)");
        IO.println("3. Binary Format (Serializable)");
        String formatStr = IO.readln("Choice (1-3): ");
        int format = 0;
        try {
            format = Integer.parseInt(formatStr.trim());
        } catch (NumberFormatException e) {
            IO.println("Invalid choice.");
            return;
        }

        String filename = IO.readln("Enter filename to save: ").trim();
        if (filename.isEmpty()) {
            IO.println("Filename cannot be empty.");
            return;
        }

        try {
            switch (format) {
                case 1:
                    if (!filename.toLowerCase().endsWith(".csv"))
                        filename += ".csv";
                    fileIO.exportToCsv(showcase.getStoredCollectibles(), filename);
                    IO.println("Successfully exported to CSV: " + filename);
                    break;
                case 2:
                    if (!filename.toLowerCase().endsWith(".json"))
                        filename += ".json";
                    fileIO.exportToJson(showcase.getStoredCollectibles(), filename);
                    IO.println("Successfully exported to JSON: " + filename);
                    break;
                case 3:
                    if (!filename.toLowerCase().endsWith(".ser"))
                        filename += ".ser";
                    fileIO.serializeCollectibles(showcase.getStoredCollectibles(), filename);
                    IO.println("Successfully exported to Binary: " + filename);
                    break;
                default:
                    IO.println("Invalid format choice.");
            }
        } catch (Exception e) {
            IO.println("Export failed: " + e.getMessage());
        }
    }

    private static void importCollectiblesMenu(Showcase showcase, CollectibleIO fileIO, ShowcaseManager manager) {
        IO.println("\nSelect import format:");
        IO.println("1. CSV Format");
        IO.println("2. Binary Format (Serializable)");
        String formatStr = IO.readln("Choice (1-2): ");
        int format = 0;
        try {
            format = Integer.parseInt(formatStr.trim());
        } catch (NumberFormatException e) {
            IO.println("Invalid choice.");
            return;
        }

        String filename = IO.readln("Enter filename to load: ").trim();
        if (filename.isEmpty()) {
            IO.println("Filename cannot be empty.");
            return;
        }

        String clearStr = IO.readln("Clear current showcase before loading? (y/n): ").trim();
        boolean clearFirst = clearStr.equalsIgnoreCase("y") || clearStr.equalsIgnoreCase("yes");

        try {
            List<PhysicalCollectible> imported = null;
            if (format == 1) {
                if (!filename.toLowerCase().endsWith(".csv"))
                    filename += ".csv";
                imported = fileIO.importFromCsv(filename);
            } else if (format == 2) {
                if (!filename.toLowerCase().endsWith(".ser"))
                    filename += ".ser";
                imported = fileIO.deserializeCollectibles(filename);
            } else {
                IO.println("Invalid format choice.");
                return;
            }

            if (imported != null) {
                if (clearFirst) {
                    showcase.clear();
                }
                int addedCount = 0;
                for (PhysicalCollectible item : imported) {
                    try {
                        manager.addCollectible(showcase, item);
                        addedCount++;
                    } catch (Exception e) {
                        IO.println("Failed to add '" + item.getName() + "': " + e.getMessage());
                    }
                }
                IO.println("Successfully imported " + addedCount + " collectibles.");
            }
        } catch (Exception e) {
            IO.println("Import failed: " + e.getMessage());
        }
    }

    private static void listSavedFiles(CollectibleIO fileIO) {
        IO.println("\n--- SAVED FILES IN DATA DIRECTORY ---");
        List<String> files = fileIO.listFiles();
        if (files.isEmpty()) {
            IO.println("No files found.");
        } else {
            for (String file : files) {
                IO.println("- " + file);
            }
        }
    }
}
