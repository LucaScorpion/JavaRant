package com.scorpiac.javarant.responses;

import com.scorpiac.javarant.Rant;

import java.util.List;

public class RantsResponse extends Response {
    private List<Rant> rants;

    public List<Rant> getRants() {
        return rants;
    }
}
