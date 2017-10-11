package com.scorpiac.javarant;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String username) {
        super("The user '" + username + "' does not exist.");
    }

    public NoSuchUserException(int id) {
        super("A user with id " + id + " does not exist.");
    }
}
