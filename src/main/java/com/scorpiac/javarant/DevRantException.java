package com.scorpiac.javarant;

public class DevRantException extends RuntimeException {
    public DevRantException(String message) {
        super(message);
    }

    public DevRantException(String message, Throwable cause) {
        super(message, cause);
    }
}
