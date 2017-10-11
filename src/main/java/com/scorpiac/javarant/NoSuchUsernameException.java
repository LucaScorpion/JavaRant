package com.scorpiac.javarant;

public class NoSuchUsernameException extends NoSuchUserException {
    private final String username;

    public NoSuchUsernameException(String username) {
        super("The user '" + username + "' does not exist.");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
