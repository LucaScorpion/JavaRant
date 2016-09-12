package com.scorpiac.javarant;

import com.google.gson.JsonObject;

public class User {
    private int id;
    private String username;
    private int score;

    public User(int id, String username, int score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }

    static User fromJson(JsonObject json) {
        return new User(
                json.get("user_id").getAsInt(),
                json.get("user_username").getAsString(),
                json.get("user_score").getAsInt()
        );
    }

    /**
     * Get the user id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the user's overall score.
     */
    public int getScore() {
        return score;
    }
}
