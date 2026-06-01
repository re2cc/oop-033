package io.re2cc.Main;

import io.re2cc.model.*;

import java.util.Optional;

public class Main {
    static void main() {
        Showcase showcase = new Showcase();

        PvcFigureCollectible defaultCollectible = new PvcFigureCollectible("Random PVC");
        PvcFigureCollectible pvcCollectible = new PvcFigureCollectible("PVC thing");
        WoodCollectible wrongMaterialCollectible = new WoodCollectible("Wood thing");
        OilPaintingCollectible specificCollectible = new OilPaintingCollectible("Painting");

        // Polymorphism, its an ArrayList<PhysicalCollectible>
        showcase.addCollectible(defaultCollectible);
        showcase.addCollectible(pvcCollectible);
        showcase.addCollectible(wrongMaterialCollectible);
        showcase.addCollectible(specificCollectible);

        showcase.showCollectibles();

        IO.println("");
        IO.println("Showcase details");
        showcase.showDisplayDetails();

        IO.println("");
        IO.println("Showcase valuation");
        double totalValue = showcase.calculateTotalValue(500.0);
        IO.println("Total estimated showcase value (base price $500.0): $" + totalValue);
        IO.println("");

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

        IO.println("");

        // Polymorphic array
        PhysicalCollectible[] collectibles = new PhysicalCollectible[] {
                new PvcFigureCollectible("Anime PVC Figure"),
                new WoodCollectible("Carved Wood Totem"),
                new OilPaintingCollectible("Sunset Landscape")
        };

        IO.println("Polymorphic Array");
        for (PhysicalCollectible item : collectibles) {
            IO.println("Item: " + item.getName());

            float maintenanceCost = item.calculateMaintenanceCost(100.0f);
            IO.println("Maintenance Cost: $" + maintenanceCost);

            IO.println("Needs special care: " + item.needsSpecialCare());
            IO.println("Preservation instructions: " + item.getPreservationInstructions());

            if (item instanceof Displayable displayable) {
                IO.println("Display setup: " + displayable.getDisplayType() +
                        " (Enclosed cabinet required: " + displayable.requiresEnclosedCabinet() + ")");
            } else {
                IO.println("Display setup: This item cannot be displayed.");
            }

            if (item instanceof Valuable valuable) {
                IO.println("Value estimate (base): $" + valuable.estimateValue(200.0));
                IO.println("Value estimate (5 years): $" + valuable.estimateValue(200.0, 5));
                IO.println("Value estimate (5 years, rare): $" + valuable.estimateValue(200.0, 5, true));
            } else {
                IO.println("Valuation: This item is not Valuable and has no market value.");
            }

            if (item instanceof PvcFigureCollectible pvc) {
                pvc.dustFigure();
            } else if (item instanceof WoodCollectible wood) {
                wood.applyVarnish();
            } else if (item instanceof OilPaintingCollectible painting) {
                painting.checkCanvasTension();
            }
            IO.println("");
        }
    }
}
