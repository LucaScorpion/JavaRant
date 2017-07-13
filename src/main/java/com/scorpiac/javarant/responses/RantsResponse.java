package com.scorpiac.javarant.responses;

import com.scorpiac.javarant.Rant;

public class RantsResponse extends Response {
    private Rant[] rants;

    RantsResponse() {
    }

    public Rant[] getRants() {
        return rants;
    }
}
