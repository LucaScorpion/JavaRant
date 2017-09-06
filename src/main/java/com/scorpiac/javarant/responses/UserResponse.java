package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.User;

public class UserResponse extends Response {
    @JsonProperty
    private User profile;

    public User getUser() {
        return profile;
    }
}
