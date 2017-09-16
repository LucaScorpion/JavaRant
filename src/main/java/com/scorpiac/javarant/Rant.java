package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.services.RequestHandler;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public class Rant extends RantContent {
    @JsonProperty
    private List<String> tags;
    @JsonProperty("num_comments")
    private int commentCount;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Rant;
    }

    /**
     * Get the link to the rant.
     *
     * @return The link to the rant.
     */
    public URI getLink() {
        return RequestHandler.BASE_URI.resolve("/rants/" + getId());
    }

    /**
     * Get the tags.
     *
     * @return The tags.
     */
    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    /**
     * Get the amount of comments.
     *
     * @return The amount of comments.
     */
    public int getCommentCount() {
        return commentCount;
    }
}
