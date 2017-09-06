package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    @JsonProperty
    private boolean success;
    @JsonProperty
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
