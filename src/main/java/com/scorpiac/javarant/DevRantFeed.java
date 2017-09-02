package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.RantsResponse;
import com.scorpiac.javarant.services.RequestHandler;
import org.apache.http.message.BasicNameValuePair;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class DevRantFeed {
    private final RequestHandler requestHandler;

    @Inject
    DevRantFeed(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public Optional<List<Rant>> getRants(Sort sort, int limit, int skip) {
        return requestHandler.get(Endpoint.RANTS, RantsResponse.class,
                new BasicNameValuePair("sort", sort.toString()),
                new BasicNameValuePair("limit", String.valueOf(limit)),
                new BasicNameValuePair("skip", String.valueOf(skip))
        )
                .map(RantsResponse::getRants);
    }
}
