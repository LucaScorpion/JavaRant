package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.RantFeedResponse;
import com.scorpiac.javarant.services.RequestHandler;
import org.apache.http.message.BasicNameValuePair;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class DevRantFeed {
    private RequestHandler requestHandler;

    @Inject
    void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public Optional<List<MinimalRant>> getRants(Sort sort, int limit, int skip) {
        return requestHandler.get(Endpoint.RANTS, RantFeedResponse.class,
                new BasicNameValuePair("sort", sort.toString()),
                new BasicNameValuePair("limit", String.valueOf(limit)),
                new BasicNameValuePair("skip", String.valueOf(skip))
        )
                .map(RantFeedResponse::getRants);
    }
}
