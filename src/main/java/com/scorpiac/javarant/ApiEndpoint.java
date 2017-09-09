package com.scorpiac.javarant;

public enum ApiEndpoint {
    API("/api"),
    API_DEVRANT(API, "devrant"),
    // Rants.
    RANTS(API_DEVRANT, "rants"),
    SEARCH(API_DEVRANT, "search"),
    SURPRISE(RANTS, "surprise"),
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
