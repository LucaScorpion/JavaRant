package com.scorpiac.javarant;

public class NoSuchUserIdException extends NoSuchUserException {
    private final int id;

    public NoSuchUserIdException(int id) {
        super("A user with id " + id + " does not exist.");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
