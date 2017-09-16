package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Comment;
import com.scorpiac.javarant.CommentedRant;

import java.lang.reflect.Field;
import java.util.List;

abstract class ResponseWithComments<T extends CommentedRant> extends Response<T> {
    private List<Comment> comments;

    @JsonProperty
    void setComments(List<Comment> comments) {
        this.comments = comments;
        setCommentsOnRant();
    }

    @JsonProperty
    void setRant(T rant) {
        value = rant;
        setCommentsOnRant();
    }

    private void setCommentsOnRant() {
        // Make sure both properties are set first.
        if (value == null || comments == null) {
            return;
        }

        // Get the comments field.
        Field commentsField;
        try {
            commentsField = CommentedRant.class.getDeclaredField("comments");
        } catch (NoSuchFieldException e) {
            // This never happens.
            throw new IllegalStateException("Could not get comments field from rant.", e);
        }

        // Set the comments field.
        commentsField.setAccessible(true);
        try {
            commentsField.set(value, comments);
        } catch (IllegalAccessException e) {
            // This never happens.
            throw new IllegalStateException("Could not set comments field on rant.", e);
        }
    }
}
