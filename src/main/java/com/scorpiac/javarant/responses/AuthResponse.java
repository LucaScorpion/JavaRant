package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Auth;

public class AuthResponse extends Response {
    @JsonProperty("auth_token")
    private Auth auth;

    public Auth getAuth() {
        return auth;
    }
}
