package com.scorpiac.javarant;

import com.scorpiac.javarant.services.RequestHandler;

import java.net.URI;

public class MinimalUser {
    private int id;
    private String username;
    private int score;

    static MinimalUser create(int id, String username, int score) {
        MinimalUser user = new MinimalUser();
        user.id = id;
        user.username = username;
        user.score = score;
        return user;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MinimalUser && ((MinimalUser) obj).getId() == id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Get the user id.
     *
     * @return The user id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the user's overall score.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the link to the user's profile.
     *
     * @return The link to the user's profile.
     */
    public URI getLink() {
        return RequestHandler.BASE_URI.resolve(DevRant.USER_URL).resolve(username);
    }
}
