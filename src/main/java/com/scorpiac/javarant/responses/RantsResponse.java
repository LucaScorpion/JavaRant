package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.News;
import com.scorpiac.javarant.Rant;

import java.util.List;

public class RantsResponse extends Response {
    @JsonProperty
    private List<Rant> rants;
    @JsonProperty
    private News news;

    public List<Rant> getRants() {
        return rants;
    }
}
