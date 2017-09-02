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

    public Optional<Rant> getRant(int id) {
        return requestHandler.get(Endpoint.RANTS.toString() + '/' + id, RantResponse.class)
                .flatMap(RantResponse::getRant);
    }

    public Optional<User> getUser(String username) {
        return requestHandler.get(Endpoint.USER_ID, UserIdResponse.class, new BasicNameValuePair("username", username))
                .flatMap(u -> getUser(u.getId()));
    }

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
}
