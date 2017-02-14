package com.scorpiac.javarant;

import com.google.gson.JsonObject;

public class Comment extends RantContent {
    protected Comment(int id, User user, int upvotes, int downvotes, String content, Image image) {
        super(id, user, upvotes, downvotes, content, image);
    }

    static Comment fromJson(JsonObject json) {
        return new Comment(
                json.get("id").getAsInt(),
                User.fromJson(json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("body").getAsString(),
                Image.fromJson(json.get("attached_image"))
        );
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Comment;
    }
}
