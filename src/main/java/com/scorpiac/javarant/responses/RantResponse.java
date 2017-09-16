package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Rant;

public class RantResponse extends Response<Rant> {
    @JsonProperty
    void setRant(Rant rant) {
        value = rant;
    }
}
