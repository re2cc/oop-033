package io.re2cc.model;

import java.util.ArrayList;
import java.util.Optional;

public class Showcase {
    private ArrayList<Collectible> storedCollectibles = new ArrayList<>();
    protected int capacity = 5; // Default capacity, other showcase types may have a different ones

    public Showcase() {}

    public void addCollectible(Collectible collectible) {
        if (storedCollectibles.size() < capacity) {
            storedCollectibles.add(collectible);
        } else {
            IO.println("Error: There is no more space in the showcase");
        }
    }

    public Optional<Collectible> searchCollectible(String name) {
        for (Collectible collectible : storedCollectibles) {
            if (collectible.getName().equals(name)) {
                return Optional.of(collectible);
            }
        }
        return Optional.empty();
    }

    public void showCollectibles() {
        for (Collectible collectible : storedCollectibles) {
            IO.println(collectible);
        }
    }
}
