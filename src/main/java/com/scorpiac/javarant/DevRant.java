package com.scorpiac.javarant;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scorpiac.javarant.responses.RantResponse;
import com.scorpiac.javarant.responses.UserIdResponse;
import com.scorpiac.javarant.responses.UserResponse;
import com.scorpiac.javarant.services.RequestHandler;
import org.apache.http.message.BasicNameValuePair;

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
        INJECTOR = Guice.createInjector();
    }

    public DevRant() {
        INJECTOR.injectMembers(this);
        feed = INJECTOR.getInstance(DevRantFeed.class);
    }

    @Inject
    void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public DevRantFeed getFeed() {
        return feed;
    }

    /**
     * Get a rant.
     *
     * @param id The id of the rant.
     * @return The rant.
     */
    public Optional<Rant> getRant(int id) {
        return requestHandler.get(Endpoint.RANTS.toString() + '/' + id, RantResponse.class)
                .flatMap(RantResponse::getRant);
    }

    /**
     * Get a user by username.
     *
     * @param username The username of the user.
     * @return The user.
     */
    public Optional<User> getUser(String username) {
        return requestHandler.get(Endpoint.USER_ID, UserIdResponse.class, new BasicNameValuePair("username", username))
                .flatMap(u -> getUser(u.getId()));
    }

    /**
     * Get a user.
     *
     * @param id The id of the user.
     * @return The user.
     */
    public Optional<User> getUser(int id) {
        Optional<User> user = requestHandler.get(Endpoint.USERS.toString() + '/' + id, UserResponse.class)
                .flatMap(UserResponse::getUser);

        // Set the id, as that is not part of the response.
        user.ifPresent(u -> u.setId(id));

        return user;
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
     * @return {@code true} if a user is logged in, or {@code false} otherwise.
     */
    public boolean isLoggedIn() {
        return auth != null;
    }

    /**
     * Set the request timeout in milliseconds, -1 meaning no timeout.
     *
     * @param timeout The timeout to use.
     */
    public void setRequestTimeout(int timeout) {
        requestHandler.setRequestTimeout(timeout);
    }

    /**
     * Get the request timeout in milliseconds.
     *
     * @return The timeout.
     */
    public int getRequestTimeout() {
        return requestHandler.getRequestTimeout();
    }
}
