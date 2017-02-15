package com.scorpiac.javarant;

import com.google.gson.JsonObject;

public class Comment extends RantContent {
    protected Comment(DevRant devRant, int id, User user, int upvotes, int downvotes, int score, String content, Image image) {
        super(devRant, id, user, upvotes, downvotes, score, content, image);
    }

    static Comment fromJson(DevRant devRant, JsonObject json) {
        return new Comment(
                devRant,
                json.get("id").getAsInt(),
                User.fromJson(devRant, json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("score").getAsInt(),
                json.get("body").getAsString(),
                Image.fromJson(json.get("attached_image"))
        );
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Comment;
    }
}
