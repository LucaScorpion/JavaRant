package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scorpiac.javarant.DevRantApiException;

import java.util.Optional;

/**
 * This class is used internally to create a pojo from a response.
 * It contains whether the request was successful, an error message and the actual result value.
 *
 * @param <T> The type of the value the response contains.
 */
public abstract class Response<T> {
    @JsonProperty
    private boolean success;
    @JsonProperty
    private String error;

    // The actual result value.
    T value;

    /**
     * Get the error message.
     *
     * @return The error message.
     */
    public String getError() {
        return error != null ? error : "An unknown error occurred.";
    }

    /**
     * Get the result value.
     *
     * @return An optional containing the result value.
     */
    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    public T getValueOrError() {
        if (!success) {
            throw new DevRantApiException(error);
        }
        return value;
    }
}
