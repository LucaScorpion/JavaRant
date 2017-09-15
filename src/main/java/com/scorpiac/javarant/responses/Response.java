package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Response<T> {
    @JsonProperty
    private boolean success;
    @JsonProperty
    private String error;

    T value;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public T getValue() {
        return value;
    }
}
