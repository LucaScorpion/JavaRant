package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.News;
import com.scorpiac.javarant.Rant;

import java.util.List;

abstract class AbstractRantsFeedResponse<T extends Rant> extends Response<List<T>> {
    @JsonProperty
    private News news; // TODO: use this.

    @JsonProperty
    void setRants(List<T> rants) {
        value = rants;
    }
}
