package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth {
    @JsonProperty
    private String id;
    @JsonProperty
    private String key;
    @JsonProperty("user_id")
    private String userId;

    String getId() {
        return id;
    }

    String getKey() {
        return key;
    }

    String getUserId() {
        return userId;
    }
}
