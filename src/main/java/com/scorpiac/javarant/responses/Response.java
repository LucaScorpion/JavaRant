package com.scorpiac.javarant.responses;

class Response {
    private boolean success;
    private String error;

    Response() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
