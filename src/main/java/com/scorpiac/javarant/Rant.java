package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.services.RequestHandler;

import java.net.URI;
import java.util.Collections;
import java.util.List;


public class Rant extends RantContent {
    private List<String> tags;
    @JsonProperty("num_comments")
    private int commentCount;
    private List<Comment> comments;
    private String link;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Rant;
    }

    /**
     * Get the comments on this rant.
     *
     * @return The comments.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    /**
     * Get the link to the rant.
     */
    public URI getLink() {
        return RequestHandler.BASE_URI.resolve('/' + link);
    }

    /**
     * Get the tags.
     */
    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    /**
     * Get the amount of comments.
     */
    public int getCommentCount() {
        return commentCount;
    }
}
