package io.re2cc.model;

public interface Valuable {
    double estimateValue(double basePrice);
    double estimateValue(double basePrice, int yearsElapsed);
    double estimateValue(double basePrice, int yearsElapsed, boolean isRare);
}
