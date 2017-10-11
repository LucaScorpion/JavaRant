package com.scorpiac.javarant;

public class NoSuchRantException extends RuntimeException {
    private final int id;

    public NoSuchRantException(int id) {
        super("A rant with id " + id + " does not exist.");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
