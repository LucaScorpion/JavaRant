package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Rant;
import com.scorpiac.javarant.News;

import java.util.List;

public class RantFeedResponse extends Response {
    @JsonProperty
    private List<Rant> rants;
    @JsonProperty
    private News news;

    public List<Rant> getRants() {
        return rants;
    }
}
