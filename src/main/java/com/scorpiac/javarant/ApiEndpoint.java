package com.scorpiac.javarant;

public enum ApiEndpoint {
    API("/api"),
    API_DEVRANT(API, "devrant"),
    // Rants.
    RANTS(API_DEVRANT, "rants"),
    SURPRISE(RANTS, "surprise"),
    SEARCH(API_DEVRANT, "search"),
    WEEKLY(API_DEVRANT, "weekly-rants"),
    STORIES(API_DEVRANT, "story-rants"),
    COLLABS(API_DEVRANT, "collabs"),
    // Users.
    USER_ID(API, "get-user-id"),
    USERS(API, "users"),
    AUTH_TOKEN(USERS, "auth-token");

    private final String endpoint;

    ApiEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    ApiEndpoint(ApiEndpoint base, String endpoint) {
        this(base.toString() + '/' + endpoint);
    }

    @Override
    public String toString() {
        return endpoint;
    }
}
