package com.scorpiac.javarant;

public enum Reason {
    NOT_FOR_ME("0"),
    REPOST("1"),
    OFFENSIVE_SPAM("2");

    private final String value;

    Reason(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
