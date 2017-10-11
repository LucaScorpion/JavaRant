package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.CollabsFeedResponse;
import com.scorpiac.javarant.responses.RantsFeedResponse;
import com.scorpiac.javarant.responses.ResultsFeedResponse;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

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
     * @param skip  How many rants to skip.
     * @return Rants from the feed.
     */
    public List<Rant> getRants(Sort sort, int limit, int skip) {
        return devRant.getRequestHandler().get(ApiEndpoint.RANTS, RantsFeedResponse.class,
                new BasicNameValuePair("sort", sort.toString()),
                new BasicNameValuePair("limit", String.valueOf(limit)),
                new BasicNameValuePair("skip", String.valueOf(skip))
        ).getValueOrError();
    }

    /**
     * Search for rants.
     *
     * @param term The term to search for.
     * @return The search results.
     */
    public List<Rant> search(String term) {
        return devRant.getRequestHandler().get(ApiEndpoint.SEARCH, ResultsFeedResponse.class,
                new BasicNameValuePair("term", term)
        ).getValueOrError();
    }

    /**
     * Get weekly rants from the feed.
     *
     * @param sort How to sort the feed.
     * @param skip How many rants to skip.
     * @return Weekly rants from the feed.
     */
    public List<Rant> getWeekly(Sort sort, int skip) {
        return devRant.getRequestHandler().get(ApiEndpoint.WEEKLY, RantsFeedResponse.class,
                new BasicNameValuePair("sort", sort.toString()),
                new BasicNameValuePair("skip", String.valueOf(skip))
        ).getValueOrError();
    }

    /**
     * Get stories from the feed.
     *
     * @param sort How to sort the feed.
     * @param skip How many rants to skip.
     * @return Stories from the feed.
     */
    public List<Rant> getStories(Sort sort, int skip) {
        return devRant.getRequestHandler().get(ApiEndpoint.STORIES, RantsFeedResponse.class,
                new BasicNameValuePair("sort", sort.toString()),
                new BasicNameValuePair("skip", String.valueOf(skip))
        ).getValueOrError();
    }

    /**
     * Get collabs from the feed.
     *
     * @param limit How many rants to get.
     * @return Collabs from the feed.
     */
    public List<Collab> getCollabs(int limit) {
        return devRant.getRequestHandler().get(ApiEndpoint.COLLABS, CollabsFeedResponse.class,
                new BasicNameValuePair("limit", String.valueOf(limit))
        ).getValueOrError();
    }
}
