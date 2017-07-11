package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.RantsResponse;
import com.scorpiac.javarant.services.RequestHandler;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DevRantFeed {
    private static final String API_RANTS = "/api/devrant/rants";

    private final RequestHandler requestHandler;

    @Inject
    DevRantFeed(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public Optional<List<Rant>> getRants() {
        return requestHandler.get(API_RANTS, RantsResponse.class).map(r -> Arrays.asList(r.getRants()));
    }
}
