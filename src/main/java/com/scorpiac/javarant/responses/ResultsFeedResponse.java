package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Rant;

import java.util.List;

public class ResultsFeedResponse extends Response {
    @JsonProperty
    private List<Rant> results;

    public List<Rant> getResults() {
        return results;
    }
}
