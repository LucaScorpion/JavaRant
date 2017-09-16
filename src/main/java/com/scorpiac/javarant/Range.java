package com.scorpiac.javarant;

public enum Range {
    DAY,
    WEEK,
    MONTH,
    ALL;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
