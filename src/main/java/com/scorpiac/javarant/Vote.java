package com.scorpiac.javarant;

public enum Vote {
    UP(1),
    NONE(0),
    DOWN(-1);

    private final int number;

    Vote(int number) {
        this.number = number;
    }

    /**
     * Get the number value of this vote.
     *
     * @return The number value.
     */
    public int getNumber() {
        return number;
    }
}
