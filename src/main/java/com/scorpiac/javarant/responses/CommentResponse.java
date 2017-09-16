package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Comment;

public class CommentResponse extends Response<Comment> {
    @JsonProperty
    void setComment(Comment comment) {
        value = comment;
    }
}
