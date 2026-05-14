package io.re2cc.main;

import io.re2cc.model.*;

import java.util.Optional;

public class main {
    static void main() {
        Showcase showcase = new Showcase();

        PhysicalCollectible defaultCollectible = new PhysicalCollectible("Random thing", 10.0f, 10.0f, 10.0f, 10.0f);
        PvcFigureCollectible pvcCollectible = new PvcFigureCollectible("PVC thing");
        WoodCollectible wrongMaterialCollectible = new WoodCollectible("Wood thing");
        OilPaintingCollectible specificCollectible = new OilPaintingCollectible("Painting");

        // Polymorphism, its an ArrayList<PhysicalCollectible>
        showcase.addCollectible(defaultCollectible);
        showcase.addCollectible(pvcCollectible);
        showcase.addCollectible(wrongMaterialCollectible);
        showcase.addCollectible(specificCollectible);

        showcase.showCollectibles();

        // Polymorphism, will return OilPaintingCollectible on a PhysicalCollectible
        Optional<PhysicalCollectible> search = showcase.searchCollectible("Painting");

        if (search.isPresent()) {
            PhysicalCollectible foundCollectible = search.get();
            foundCollectible.elevateThreshold(0.1f);
            IO.println(foundCollectible);
            IO.println("Approximated UV index: " + foundCollectible.uvIndexAproxThreshold());
            IO.println("Suggested temperature: " + foundCollectible.getSuggestedTemperature());
            if (foundCollectible.hasExceedTemperatureThreshold(150f)) {
                IO.println("Warning: The temperature has exceeded the maximum temperature threshold.");
            }
        }
    }
}
