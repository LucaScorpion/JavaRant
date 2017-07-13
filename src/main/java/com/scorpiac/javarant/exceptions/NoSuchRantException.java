package com.scorpiac.javarant.exceptions;

public class NoSuchRantException extends DevRantApiException {
    /**
     * Create a new exception for a non-existent rant.
     *
     * @param id The id of the rant.
     */
    public NoSuchRantException(int id, String internalError) {
        super("Rant with id " + id + " does not exist.", internalError);
    }
}
