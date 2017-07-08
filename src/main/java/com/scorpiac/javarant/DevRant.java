package com.scorpiac.javarant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DevRant {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevRant.class);

    static final String APP_ID = "3";
    static final String PLAT_ID = "3";

    static final String BASE_URL = "https://www.devrant.io";
    static final String AVATARS_URL = "https://avatars.devrant.io";

    static final String USER_URL = "/users";
    static final String RANT_URL = "/rants";
    static final String COLLAB_URL = "/collabs";

    // API endpoints.
    static final String API = "/api";
    static final String API_DEVRANT = API + "/devrant";
    static final String API_RANTS = API_DEVRANT + "/rants";
    static final String API_SEARCH = API_DEVRANT + "/search";
    static final String API_SURPRISE = API_RANTS + "/surprise";
    static final String API_USERS = API + "/users";
    static final String API_USER_ID = API + "/get-user-id";
    static final String API_WEEKLY = API_DEVRANT + "/weekly-rants";
    static final String API_COLLABS = API_DEVRANT + "/collabs";
    static final String API_STORIES = API_DEVRANT + "/story-rants";
    static final String API_AUTH_TOKEN = API_USERS + "/auth-token";
    static final String API_COMMENT = "/comments";
    static final String API_VOTE = "/vote";
    static final String API_NOTIFS = API_USERS + "/me/notif-feed";

    private Auth auth;
    private int timeout = 15000;
    private boolean hideReposts = false;
    private int numNotifs;
    private News news;
    private int weeklyRantNumber = -1;

    /**
     * Log out of devRant.
     */
    public void logout() {
        auth = null;
    }

    /**
     * Check whether a user is logged in.
     *
     * @return {@code true} if a user is logged in.
     */
    public boolean isLoggedIn() {
        return auth != null;
    }

    /**
     * Get a list with all the parameters, including default and auth parameters.
     * This also filters out any parameters that are {@code null}.
     *
     * @param params The parameters to use.
     * @return A list containing the given parameters, the default parameters, and the auth parameters.
     */
    private List<NameValuePair> getParameters(NameValuePair... params) {
        List<NameValuePair> paramList = new ArrayList<>(params.length + 6);
        paramList.addAll(Arrays.stream(params).filter(p -> p != null).collect(Collectors.toList()));

        // Add the parameters which always need to be present.
        paramList.add(new BasicNameValuePair("app", APP_ID));
        paramList.add(new BasicNameValuePair("plat", PLAT_ID));
        paramList.add(new BasicNameValuePair("hide_reposts", hideReposts ? "1" : "0"));

        // Add the auth information.
        if (isLoggedIn()) {
            paramList.add(new BasicNameValuePair("token_id", auth.getId()));
            paramList.add(new BasicNameValuePair("token_key", auth.getKey()));
            paramList.add(new BasicNameValuePair("user_id", auth.getUserId()));
        }

        return paramList;
    }

    /**
     * Set the request timeout. This timeout will be used for the socket and connection timeout.
     *
     * @param timeout The timeout in milliseconds to set, or -1 to set no timeout.
     */
    public void setRequestTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Get the current request timeout in milliseconds.
     */
    public int getRequestTimeout() {
        return timeout;
    }

    /**
     * Set whether to hide reposts.
     *
     * @param hideReposts Whether to hide reposts.
     */
    public void setHideReposts(boolean hideReposts) {
        this.hideReposts = hideReposts;
    }

    /**
     * Get whether to hide reposts.
     */
    public boolean getHideReposts() {
        return hideReposts;
    }

    /**
     * Get the amount of notifications the user has.
     */
    public int getNotifCount() {
        return numNotifs;
    }

    /**
     * Get the news.
     */
    public News getNews() {
        return news;
    }

    /**
     * Get the weekly rant number, or -1 if this has not yet been set.
     */
    public int getWeeklyRantNumber() {
        return weeklyRantNumber;
    }
}
