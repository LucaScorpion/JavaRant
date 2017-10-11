package com.scorpiac.javarant;

public class DevRantApiException extends RuntimeException {
    public DevRantApiException(String message) {
        super("A devRant API exception occurred: " + message);
    }
}
