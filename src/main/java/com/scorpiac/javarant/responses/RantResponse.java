package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Rant;

import java.util.Optional;

public class RantResponse extends Response {
    @JsonProperty
    private Rant rant;

    public Optional<Rant> getRant() {
        return Optional.ofNullable(rant);
    }
}
