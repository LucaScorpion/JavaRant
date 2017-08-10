package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.RantsResponse;
import com.scorpiac.javarant.services.RequestHandler;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class DevRantFeed {
    private final RequestHandler requestHandler;

    @Inject
    DevRantFeed(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public Optional<List<Rant>> getRants() {
        return requestHandler.get(Endpoint.RANTS, RantsResponse.class).map(RantsResponse::getRants);
    }
}
