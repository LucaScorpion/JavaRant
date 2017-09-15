package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.Response;

import java.util.Optional;

public class Result<T> {
    private T value;
    private String error;

    public Result(String error) {
        this.error = error;
        value = null;
    }

    public Result(Response<T> response) {
        error = response.getError();
        value = response.getValue();
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }
}
