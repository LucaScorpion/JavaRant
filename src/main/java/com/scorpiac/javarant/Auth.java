package com.scorpiac.javarant;

class Auth {
    private String id;
    private String key;
    private String userId;

    Auth(String id, String key, String userId) {
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
