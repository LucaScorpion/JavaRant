package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.MinimalRant;
import com.scorpiac.javarant.News;

import java.util.List;

public class RantFeedResponse extends Response {
    @JsonProperty
    private List<MinimalRant> rants;
    @JsonProperty
    private News news;

    public List<MinimalRant> getRants() {
        return rants;
    }
}
