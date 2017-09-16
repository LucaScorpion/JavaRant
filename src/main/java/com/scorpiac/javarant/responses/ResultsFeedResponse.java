package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Rant;

import java.util.List;

public class ResultsFeedResponse extends Response<List<Rant>> {
    @JsonProperty
    void setResults(List<Rant> results) {
        value = results;
    }
}
