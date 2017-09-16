package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth {
    private final String id;
    private final String key;
    private final String userId;

    Auth(@JsonProperty("id") String id, @JsonProperty("key") String key, @JsonProperty("user_id") String userId) {
        this.id = id;
        this.key = key;
        this.userId = userId;
    }

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
