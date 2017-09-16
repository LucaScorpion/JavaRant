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

    /**
     * Get the result value.
     * If an error occurred, this will be empty and the error will be set.
     *
     * @return The value.
     */
    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    /**
     * Get the error message.
     * If there was no error, this returns {@code null}.
     *
     * @return The error, or {@code null} if there was none.
     */
    public String getError() {
        return error;
    }
}
