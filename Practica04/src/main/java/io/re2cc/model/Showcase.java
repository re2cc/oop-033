package io.re2cc.model;

import java.util.ArrayList;
import java.util.Optional;

public class Showcase {
    private ArrayList<PhysicalCollectible> storedCollectibles = new ArrayList<>();
    protected int capacity = 5; // Default capacity, other showcase types may have a different ones

    public Showcase() {}

    public void addCollectible(PhysicalCollectible physicalCollectible) {
        if (storedCollectibles.size() < capacity) {
            storedCollectibles.add(physicalCollectible);
        } else {
            IO.println("Error: There is no more space in the showcase");
        }
    }

    public Optional<PhysicalCollectible> searchCollectible(String name) {
        for (PhysicalCollectible physicalCollectible : storedCollectibles) {
            if (physicalCollectible.getName().equals(name)) {
                return Optional.of(physicalCollectible);
            }
        }
        return Optional.empty();
    }

    public void showCollectibles() {
        for (Collectible physicalCollectible : storedCollectibles) {
            IO.println(physicalCollectible);
        }
    }
}
