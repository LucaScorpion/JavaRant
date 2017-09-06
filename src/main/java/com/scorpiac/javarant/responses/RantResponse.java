package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Rant;

public class RantResponse extends Response {
    @JsonProperty
    private Rant rant;

    public Rant getRant() {
        return rant;
    }
}
