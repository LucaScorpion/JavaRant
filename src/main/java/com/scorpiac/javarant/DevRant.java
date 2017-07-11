package com.scorpiac.javarant;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class DevRant {
    private static final Injector INJECTOR;

    static final String USER_URL = "/users";
    static final String RANT_URL = "/rants";
    static final String COLLAB_URL = "/collabs";

    private Auth auth;

    private final DevRantFeed feed;

    static {
        INJECTOR = Guice.createInjector(new InjectionModule());
    }

    public DevRant() {
        INJECTOR.injectMembers(this);
        feed = INJECTOR.getInstance(DevRantFeed.class);
    }

    public DevRantFeed getFeed() {
        return feed;
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
}
