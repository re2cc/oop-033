package io.re2cc.exception;

public class ShowcaseCapacityExceededException extends CollectibleException {
    private int maxCapacity;
    private int currentCount;

    public ShowcaseCapacityExceededException(String message, int maxCapacity, int currentCount) {
        super(message);
        this.maxCapacity = maxCapacity;
        this.currentCount = currentCount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCount() {
        return currentCount;
    }
}
