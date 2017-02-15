package com.scorpiac.javarant;

import com.google.gson.JsonObject;

class Auth {
    private String id;
    private String key;
    private String userId;

    Auth(String id, String key, String userId) {
        this.id = id;
        this.key = key;
        this.userId = userId;
    }

    static Auth fromJson(JsonObject json) {
        JsonObject token = json.get("auth_token").getAsJsonObject();
        return new Auth(token.get("id").getAsString(), token.get("key").getAsString(), token.get("user_id").getAsString());
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
