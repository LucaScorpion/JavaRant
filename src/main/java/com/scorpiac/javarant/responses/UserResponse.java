package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.User;

import java.util.Optional;

public class UserResponse extends Response {
    @JsonProperty
    private User profile;

    public Optional<User> getUser() {
        return Optional.ofNullable(profile);
    }
}
