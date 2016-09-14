package com.scorpiac.javarant.exceptions;

public class NoSuchRantException extends RuntimeException {
    /**
     * Create a new exception for a non-existent rant.
     *
     * @param id The id of the rant.
     */
    public NoSuchRantException(int id) {
        super("Rant with id " + id + " does not exist.");
    }
}
