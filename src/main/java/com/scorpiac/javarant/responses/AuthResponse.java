package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.Auth;

public class AuthResponse extends Response<Auth> {
    @JsonProperty("auth_token")
    void setAuth(Auth auth) {
        value = auth;
    }
}
