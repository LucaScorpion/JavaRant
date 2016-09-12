package com.scorpiac.javarant;

import com.google.gson.JsonObject;

public class Comment extends RantContent {
    public Comment(String author, int score, String content) {
        super(author, score, content);
    }

    static Comment fromJson(JsonObject json) {
        return new Comment(
                json.get("user_username").getAsString(),
                json.get("score").getAsInt(),
                json.get("body").getAsString()
        );
    }
}
