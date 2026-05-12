package io.re2cc.practica02;

import java.util.ArrayList;
import java.util.Optional;

public class Showcase {
    ArrayList<Collectible> storedCollectibles = new ArrayList<>();

    Showcase() {}

    void addCollectible(Collectible collectible) {
        storedCollectibles.add(collectible);
    }

    Optional<Collectible> searchCollectible(String name) {
        for (Collectible collectible : storedCollectibles) {
            if (collectible.name.equals(name)) {
                return Optional.of(collectible);
            }
        }
        return Optional.empty();
    }

    void showCollectibles() {
        for (Collectible collectible : storedCollectibles) {
            IO.println(collectible.name);
        }
    }
}
