package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIdResponse extends Response {
    @JsonProperty("user_id")
    void setId(int id) {
        value = id;
    }
}
