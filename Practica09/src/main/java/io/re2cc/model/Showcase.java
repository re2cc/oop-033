package io.re2cc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import java.util.Optional;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Showcase {
    private final ArrayList<PhysicalCollectible> storedCollectibles = new ArrayList<>();

    private final HashMap<String, PhysicalCollectible> collectibleByName = new HashMap<>();

    private final HashSet<String> uniqueTags = new HashSet<>();

    private final LinkedList<String> maintenanceHistory = new LinkedList<>();

    protected int capacity = 5;

    public ArrayList<PhysicalCollectible> getStoredCollectibles() {
        return storedCollectibles;
    }

    public int getCapacity() {
        return capacity;
    }

    public Showcase() {
    }

    public Showcase(int capacity) {
        this.capacity = capacity;
    }

    public HashSet<String> getUniqueTags() {
        return uniqueTags;
    }

    public LinkedList<String> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    public void addCollectible(PhysicalCollectible physicalCollectible) {
        if (storedCollectibles.size() < capacity) {
            storedCollectibles.add(physicalCollectible);
            collectibleByName.put(physicalCollectible.getName(), physicalCollectible);
            extractTags(physicalCollectible);
            maintenanceHistory.add("Added: " + physicalCollectible.getName() + " ["
                    + physicalCollectible.getClass().getSimpleName() + "]");
        } else {
            IO.println("Error: There is no more space in the showcase");
        }
    }

    private void extractTags(PhysicalCollectible item) {
        if (item instanceof PvcFigureCollectible) {
            uniqueTags.add("PVC");
        } else if (item instanceof WoodCollectible) {
            uniqueTags.add("Wood");
        } else if (item instanceof OilPaintingCollectible) {
            uniqueTags.add("Painting");
        }

        if (item instanceof Valuable) {
            uniqueTags.add("Valuable");
        }
        if (item instanceof Displayable) {
            uniqueTags.add("Displayable");
        }
        if (item.getTemperatureThreshold() < 30.0f || item.getHumidityThreshold() < 35.0f) {
            uniqueTags.add("Sensitive");
        }
    }

    private void rebuildUniqueTags() {
        uniqueTags.clear();
        for (PhysicalCollectible item : storedCollectibles) {
            extractTags(item);
        }
    }

    public Optional<PhysicalCollectible> searchCollectible(String name) {
        return Optional.ofNullable(collectibleByName.get(name));
    }

    public boolean updateCollectibleThresholds(String name, float newTemp, float newHum) {
        Optional<PhysicalCollectible> opt = searchCollectible(name);
        if (opt.isPresent()) {
            PhysicalCollectible item = opt.get();
            float oldTemp = item.getTemperatureThreshold();
            float oldHum = item.getHumidityThreshold();
            item.setTemperatureThreshold(newTemp);
            item.setHumidityThreshold(newHum);
            maintenanceHistory.add("Updated: " + name + " (Temp: " + oldTemp + "°C -> " + newTemp + "°C, Hum: " + oldHum
                    + "% -> " + newHum + "%)");
            return true;
        }
        return false;
    }

    public boolean deleteCollectible(String name) {
        boolean removed = false;
        Iterator<PhysicalCollectible> iterator = storedCollectibles.iterator();
        while (iterator.hasNext()) {
            PhysicalCollectible item = iterator.next();
            if (item.getName().equals(name)) {
                iterator.remove();
                collectibleByName.remove(name);
                maintenanceHistory.add("Removed: " + name);
                rebuildUniqueTags();
                removed = true;
                break;
            }
        }
        return removed;
    }

    public void removeCollectiblesExceedingHumidity(float humidityLimit) {
        Iterator<PhysicalCollectible> iterator = storedCollectibles.iterator();
        boolean removedAny = false;
        while (iterator.hasNext()) {
            PhysicalCollectible item = iterator.next();
            if (item.getHumidityThreshold() > humidityLimit) {
                IO.println("Removing sensitive item due to high humidity limit: " + item.getName());
                collectibleByName.remove(item.getName());
                maintenanceHistory.add("Bulk Removed (Humidity Limit " + humidityLimit + "%): " + item.getName());
                iterator.remove();
                removedAny = true;
            }
        }
        if (removedAny) {
            rebuildUniqueTags();
        }
    }

    public List<PhysicalCollectible> findSensitiveCollectibles(float maxTemp, float maxHumidity) {
        return storedCollectibles.stream()
                .filter(c -> c.getTemperatureThreshold() < maxTemp)
                .filter(c -> c.getHumidityThreshold() < maxHumidity)
                .collect(Collectors.toList());
    }

    public List<PhysicalCollectible> getSortedCollectibles(Comparator<PhysicalCollectible> comparator) {
        return storedCollectibles.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public void showCollectibles() {
        for (Collectible physicalCollectible : storedCollectibles) {
            IO.println(physicalCollectible);
        }
    }

    public void showDisplayDetails() {
        for (PhysicalCollectible item : storedCollectibles) {
            if (item instanceof Displayable displayable) {
                IO.println("Collectible '" + item.getName() + "' Display: " + displayable.getDisplayType() +
                        " (Enclosed Cabinet required: " + displayable.requiresEnclosedCabinet() + ")");
            } else {
                IO.println("Collectible '" + item.getName() + "' cannot be displayed.");
            }
        }
    }

    public double calculateTotalValue(double basePrice) {
        double total = 0.0;
        for (PhysicalCollectible item : storedCollectibles) {
            if (item instanceof Valuable valuable) {
                total += valuable.estimateValue(basePrice);
            }
        }
        return total;
    }
}
