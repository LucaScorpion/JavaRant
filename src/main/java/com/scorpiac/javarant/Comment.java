package com.scorpiac.javarant;

import com.google.gson.JsonObject;

public class Comment extends RantContent {
    public Comment(int id, User user, int upvotes, int downvotes, String content) {
        super(id, user, upvotes, downvotes, content);
    }

    static Comment fromJson(JsonObject json) {
        return new Comment(
                json.get("id").getAsInt(),
                User.fromJson(json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("body").getAsString()
        );
    }
}
