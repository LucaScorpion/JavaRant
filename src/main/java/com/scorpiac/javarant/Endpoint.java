package com.scorpiac.javarant;

public enum Endpoint {
    API("/api"),
    API_DEVRANT(API, "devrant"),
    USER_ID(API, "get-user-id"),
    USERS(API, "users"),
    RANTS(API_DEVRANT, "rants"),
    SEARCH(API_DEVRANT, "search"),
    SURPRISE(RANTS, "surprise");

    private final String endpoint;

    Endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    Endpoint(Endpoint base, String endpoint) {
        this(base.toString() + '/' + endpoint);
    }

    @Override
    public String toString() {
        return endpoint;
    }
}
