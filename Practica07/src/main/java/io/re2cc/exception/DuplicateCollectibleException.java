package io.re2cc.exception;

public class DuplicateCollectibleException extends CollectibleException {
    private String duplicateName;

    public DuplicateCollectibleException(String message, String duplicateName) {
        super(message);
        this.duplicateName = duplicateName;
    }

    public String getDuplicateName() {
        return duplicateName;
    }
}
