package io.re2cc.Main;

import io.re2cc.model.*;
import io.re2cc.controller.*;
import io.re2cc.exception.*;

public class Main {
    static void main() {
        Showcase showcase = new Showcase();
        ShowcaseManager manager = new ShowcaseManager();

        PvcFigureCollectible defaultCollectible = new PvcFigureCollectible("Random PVC");
        PvcFigureCollectible pvcCollectible = new PvcFigureCollectible("PVC thing");
        WoodCollectible wrongMaterialCollectible = new WoodCollectible("Wood thing");
        OilPaintingCollectible specificCollectible = new OilPaintingCollectible("Painting");

        IO.println("Safe additions");
        try {
            manager.addCollectible(showcase, defaultCollectible);
            manager.addCollectible(showcase, pvcCollectible);
            manager.addCollectible(showcase, wrongMaterialCollectible);
            manager.addCollectible(showcase, specificCollectible);
            IO.println("Collectibles added successfully.");

            IO.println("");
            IO.println("Triggering duplicate collectible exception");
            manager.addCollectible(showcase, new PvcFigureCollectible("PVC thing"));
        } catch (DuplicateCollectibleException e) {
            IO.println("Caught duplicate exception: " + e.getMessage() + " (duplicate: " + e.getDuplicateName() + ")");
        } catch (CollectibleException e) {
            IO.println("Unexpected exception: " + e.getMessage());
        }

        IO.println("");
        IO.println("Triggering showcase capacity exceeded exception");
        try {
            manager.addCollectible(showcase, new PvcFigureCollectible("Extra item 1"));
            manager.addCollectible(showcase, new PvcFigureCollectible("Extra item 2"));
        } catch (ShowcaseCapacityExceededException e) {
            IO.println("Caught capacity exception: " + e.getMessage() + " (max: " + e.getMaxCapacity() + ", count: "
                    + e.getCurrentCount() + ")");
        } catch (CollectibleException e) {
            IO.println("Unexpected exception: " + e.getMessage());
        }

        IO.println("");
        IO.println("Monitoring environment (try-with-resources)");
        manager.monitorEnvironment(showcase, "SensorTest");
    }
}
