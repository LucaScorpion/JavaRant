package com.scorpiac.javarant;

public enum Sort {
    ALGO,
    RECENT,
    TOP;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
