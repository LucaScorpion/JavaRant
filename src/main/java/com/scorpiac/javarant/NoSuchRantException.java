package com.scorpiac.javarant;

public class NoSuchRantException extends RuntimeException {
    public NoSuchRantException(int id) {
        super("A rant with id " + id + " does not exist.");
    }
}
