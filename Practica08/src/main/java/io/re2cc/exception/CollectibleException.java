package io.re2cc.exception;

public class CollectibleException extends Exception {
    public CollectibleException(String message) {
        super(message);
    }

    public CollectibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
