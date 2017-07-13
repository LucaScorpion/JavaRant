package com.scorpiac.javarant;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scorpiac.javarant.exceptions.NoSuchRantException;
import com.scorpiac.javarant.responses.RantResponse;
import com.scorpiac.javarant.services.RequestHandler;

import javax.inject.Inject;
import java.util.Optional;

public class DevRant {
    private static final Injector INJECTOR;

    static final String USER_URL = "/users";
    static final String RANT_URL = "/rants";
    static final String COLLAB_URL = "/collabs";

    private final DevRantFeed feed;

    private RequestHandler requestHandler;
    private Auth auth;

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

    public Optional<Rant> getRant(int id) {
        // Execute the request.
        Optional<RantResponse> response = requestHandler.get(Endpoint.RANTS.toString() + '/' + id, RantResponse.class);
        if (!response.isPresent()) {
            return Optional.empty();
        }

        // Check if the rant exists.
        RantResponse rantResponse = response.get();
        if (!rantResponse.isSuccess()) {
            throw new NoSuchRantException(id, rantResponse.getError());
        }

        return Optional.of(rantResponse.getRant());
    }

    @Inject
    void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
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
