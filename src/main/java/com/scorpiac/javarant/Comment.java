package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment extends RantContent {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Comment;
    }

    // For comments the text is called "body" instead of "text".
    @JsonProperty("body")
    private void setText(String text) {
        this.text = text;
    }
}
