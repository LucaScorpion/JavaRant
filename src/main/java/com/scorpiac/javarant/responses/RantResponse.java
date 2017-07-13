package com.scorpiac.javarant.responses;

import com.scorpiac.javarant.Rant;

public class RantResponse extends Response {
    private Rant rant;

    RantResponse() {
    }

    public Rant getRant() {
        return rant;
    }
}
