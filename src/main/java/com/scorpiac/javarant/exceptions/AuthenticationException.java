package com.scorpiac.javarant.exceptions;

public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super("Invalid login data specified.");
    }
}
