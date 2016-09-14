package com.scorpiac.javarant.exceptions;

public class NoSuchUserException extends RuntimeException {
    /**
     * Create a new exception for a non-existent user.
     *
     * @param username The username of the user.
     */
    public NoSuchUserException(String username) {
        super("User \"" + username + "\" does not exist.");
    }

    /**
     * Create a new exception for a non-existent user.
     *
     * @param id The id of the user.
     */
    public NoSuchUserException(int id) {
        super("User with id " + id + " does not exist.");
    }
}
