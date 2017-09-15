package com.scorpiac.javarant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

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
     * Get the error.
     * Returns {@code null} if there is no error.
     *
     * @return The error, or {@code null} if there is no error.
     */
    public String getError() {
        if (success) {
            return null;
        }

        return error != null ? error : "An unknown error occurred.";
    }

    /**
     * Get the result value.
     *
     * @return The result value, or {@code null} if there was an error.
     */
    public T getValue() {
        return value;
    }
}
