package com.scorpiac.javarant;

public enum Endpoint {
    API("/api"),
    API_DEVRANT(API, "devrant"),
    RANTS(API_DEVRANT, "rants");

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
