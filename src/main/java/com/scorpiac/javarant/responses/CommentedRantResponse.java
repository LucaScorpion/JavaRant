package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Comment;
import com.scorpiac.javarant.CommentedRant;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class CommentedRantResponse extends Response {
    @JsonProperty
    private CommentedRant rant;
    @JsonProperty
    private List<Comment> comments;

    public Optional<CommentedRant> getRant() {
        if (!isSuccess()) {
            return Optional.empty();
        }

        // Get the comments field.
        Field commentsField;
        try {
            commentsField = rant.getClass().getDeclaredField("comments");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e); // TODO
        }

        // Set the comments field.
        commentsField.setAccessible(true);
        try {
            commentsField.set(rant, comments);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e); // TODO
        }

        return Optional.of(rant);
    }
}
