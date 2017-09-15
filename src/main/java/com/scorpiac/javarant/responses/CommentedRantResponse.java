package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Comment;
import com.scorpiac.javarant.CommentedRant;

import java.lang.reflect.Field;
import java.util.List;

public class CommentedRantResponse extends Response<CommentedRant> {
    @JsonProperty
    private CommentedRant rant;
    @JsonProperty
    private List<Comment> comments;

    @Override
    public CommentedRant getValue() {
        // Get the comments field.
        Field commentsField;
        try {
            commentsField = rant.getClass().getDeclaredField("comments");
        } catch (NoSuchFieldException e) {
            // This never happens.
            throw new IllegalStateException("Could not get comments field from rant.", e);
        }

        // Set the comments field.
        commentsField.setAccessible(true);
        try {
            commentsField.set(rant, comments);
        } catch (IllegalAccessException e) {
            // This never happens.
            throw new IllegalStateException("Could not set comments field on rant.", e);
        }

        return rant;
    }
}
