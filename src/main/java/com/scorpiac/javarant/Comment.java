package com.scorpiac.javarant;

import com.google.gson.JsonObject;

public class Comment extends RantContent {
    public Comment(String author, int upvotes, int downvotes, String content) {
        super(author, upvotes, downvotes, content);
    }

    static Comment fromJson(JsonObject json) {
        return new Comment(
                json.get("user_username").getAsString(),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("body").getAsString()
        );
    }
}
