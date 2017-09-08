package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.RantFeedResponse;
import com.scorpiac.javarant.responses.ResultsFeedResponse;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;
import java.util.Optional;

public class DevRantFeed {
    private final DevRant devRant;

    DevRantFeed(DevRant devRant) {
        this.devRant = devRant;
    }

    /**
     * Get rants from the feed.
     *
     * @param sort  How to sort the feed.
     * @param limit How many rants to get.
     * @return Rants from the feed.
     */
    public Optional<List<Rant>> getRants(Sort sort, int limit) {
        return getRants(sort, limit, 0);
    }

    /**
     * Get rants from the feed.
     *
     * @param sort  How to sort the feed.
     * @param limit How many rants to get.
     * @param skip  How many rants to skip.
     * @return Rants from the feed.
     */
    public Optional<List<Rant>> getRants(Sort sort, int limit, int skip) {
        return devRant.getRequestHandler().get(Endpoint.RANTS, RantFeedResponse.class,
                new BasicNameValuePair("sort", sort.toString()),
                new BasicNameValuePair("limit", String.valueOf(limit)),
                new BasicNameValuePair("skip", String.valueOf(skip))
        )
                .map(RantFeedResponse::getRants);
    }

    /**
     * Search for rants.
     *
     * @param term The term to search for.
     * @return The search results.
     */
    public Optional<List<Rant>> search(String term) {
        return devRant.getRequestHandler().get(Endpoint.SEARCH, ResultsFeedResponse.class,
                new BasicNameValuePair("term", term)
        )
                .map(ResultsFeedResponse::getResults);
    }
}
