package com.scorpiac.javarant;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class DevRant {
    private static final Injector INJECTOR;

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

    static {
        INJECTOR = Guice.createInjector(new InjectionModule());
    }

    public DevRant() {
        INJECTOR.injectMembers(this);
    }

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
