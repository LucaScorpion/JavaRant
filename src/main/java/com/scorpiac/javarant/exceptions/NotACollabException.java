package com.scorpiac.javarant.exceptions;

public class NotACollabException extends RuntimeException {
    /**
     * Create a new exception for a rant that is not a collab.
     *
     * @param id The id of the rant.
     */
    public NotACollabException(int id) {
        super("Rant with id " + id + " is not a collab.");
    }
}
