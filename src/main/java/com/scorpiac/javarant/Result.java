package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.Response;

import java.util.Optional;

public class Result<T> {
    private final T value;
    private final String error;

    public Result(String error) {
        this.error = error;
        value = null;
    }

    public Result(Response<T> response) {
        if (!response.isSuccess() || (response.getError() != null && !response.getError().isEmpty())) {
            // An error occurred.
            error = response.getError();
            value = null;
        } else {
            // Success.
            error = null;
            value = response.getValue();
        }
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }
}
