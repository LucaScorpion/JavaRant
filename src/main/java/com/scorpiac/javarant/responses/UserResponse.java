package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.User;

public class UserResponse extends Response {
    @JsonProperty
    void setProfile(User profile) {
        value = profile;
    }
}
