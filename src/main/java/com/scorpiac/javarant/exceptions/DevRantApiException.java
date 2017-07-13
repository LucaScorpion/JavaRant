package com.scorpiac.javarant.exceptions;

public class DevRantApiException extends RuntimeException {
    private final String internalError;

    public DevRantApiException(String message, String internalError) {
        super(message);
        this.internalError = internalError;
    }

    public String getInternalError() {
        return internalError;
    }
}
