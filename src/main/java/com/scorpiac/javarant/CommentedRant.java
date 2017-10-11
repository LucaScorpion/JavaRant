package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class CommentedRant extends Rant {
    @JsonProperty
    private List<Comment> comments;

    /**
     * Get the comments on this rant.
     *
     * @return The comments.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
