package com.scorpiac.javarant;

public enum Vote {
    UP(1),
    NONE(0),
    DOWN(-1);

    private final int value;

    private Vote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
