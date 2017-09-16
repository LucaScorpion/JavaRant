package com.scorpiac.javarant;

import java.util.HashMap;
import java.util.Map;

public enum VoteState {
    UP,
    NONE,
    DOWN;

    private static final Map<Integer, VoteState> states = new HashMap<>();

    static {
        states.put(1, UP);
        states.put(0, NONE);
        states.put(-1, DOWN);
    }

    /**
     * Get the {@link VoteState} corresponding with a number value.
     *
     * @param value The value to get the state for.
     * @return The {@link VoteState} belonging to the value.
     */
    public static VoteState fromValue(int value) {
        return states.get(value);
    }
}
