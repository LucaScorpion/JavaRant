package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.News;
import com.scorpiac.javarant.Rant;

import java.util.List;

public class RantFeedResponse extends Response<List<Rant>> {
    @JsonProperty
    private News news; // TODO: use this.

    @JsonProperty
    void setRants(List<Rant> rants) {
        value = rants;
    }
}
