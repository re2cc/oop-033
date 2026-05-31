package io.re2cc.model;

public interface Preservable {
    boolean needsSpecialCare();
    String getPreservationInstructions();
}
