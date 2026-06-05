package io.re2cc.exception;

import java.time.Instant;

public class PreservationThresholdViolatedException extends CollectibleException {
    private String collectibleName;
    private String parameterName;
    private float thresholdLimit;
    private float actualValue;
    private Instant timestamp;

    public PreservationThresholdViolatedException(String message, String collectibleName, String parameterName,
                                                   float thresholdLimit, float actualValue) {
        super(message);
        this.collectibleName = collectibleName;
        this.parameterName = parameterName;
        this.thresholdLimit = thresholdLimit;
        this.actualValue = actualValue;
        this.timestamp = Instant.now();
    }

    public String getCollectibleName() {
        return collectibleName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public float getThresholdLimit() {
        return thresholdLimit;
    }

    public float getActualValue() {
        return actualValue;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
