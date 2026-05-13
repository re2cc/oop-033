package io.re2cc.practica03;

public class ShowcaseMonitor {
    static void main() {
        Showcase showcase = new Showcase();

        Collectible defaultCollectible = new Collectible("Random thing");
        Collectible pvcCollectible = new Collectible("PVC thing", "PVC");
        Collectible wrongMaterialCollectible = new Collectible("Glass thing", "Glass");
        Collectible specificCollectible = new Collectible("Important thing", 0.3f, 2.3f, 0.44f, 12.33f);

        showcase.addCollectible(defaultCollectible);
        showcase.addCollectible(pvcCollectible);
        showcase.addCollectible(wrongMaterialCollectible);
        showcase.addCollectible(specificCollectible);

        showcase.showCollectibles();

        if (showcase.searchCollectible("Important thing").isPresent()) {
            Collectible foundCollectible = showcase.searchCollectible("Important thing").get();
            foundCollectible.elevateThreshold(0.1f);
            foundCollectible.printThreshold();
            IO.println("Approximated UV index: " + foundCollectible.uvIndexAproxThreshold());
            IO.println("Suggested temperature: " + foundCollectible.getSuggestedTemperature());
            if (foundCollectible.hasExceedTemperatureThreshold(150f)) {
                IO.println("Warning: The temperature has exceeded the maximum temperature threshold.");
            }
        }
    }
}
