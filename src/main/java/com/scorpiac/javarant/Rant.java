package com.scorpiac.javarant;

import java.util.Collections;
import java.util.List;

public class Rant extends MinimalRant {
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
