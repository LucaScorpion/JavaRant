package com.scorpiac.javarant;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scorpiac.javarant.responses.*;
import com.scorpiac.javarant.services.RequestHandler;
import org.apache.http.message.BasicNameValuePair;

import javax.inject.Inject;

public class DevRant {
    private static final Injector INJECTOR;

    private final DevRantFeed devRantFeed;
    private final DevRantAuth devRantAuth;

    private RequestHandler requestHandler;
    Auth auth;

    static {
        INJECTOR = Guice.createInjector();
    }

    /**
     * Create a new DevRant instance.
     * Each DevRant instance has their own authenticated user.
     */
    public DevRant() {
        INJECTOR.injectMembers(this);
        devRantFeed = new DevRantFeed(this);
        devRantAuth = new DevRantAuth(this);
    }

    @Inject
    void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    RequestHandler getRequestHandler() {
        return requestHandler;
    }

    /**
     * Access the devRant feed.
     *
     * @return A devRant feed class.
     */
    public DevRantFeed getFeed() {
        return devRantFeed;
    }

    /**
     * Access the user's authenticated devRant.
     * Trying to access this when the user is not logged in will throw an {@link IllegalStateException}.
     *
     * @return An authenticated devRant class.
     */
    public DevRantAuth getAuth() {
        // Check if the user is logged in.
        if (!isLoggedIn()) {
            throw new IllegalStateException("The user must be logged in to access the DevRantAuth.");
        }

        return devRantAuth;
    }

    /**
     * Get a rant.
     *
     * @param id The id of the rant.
     * @return The rant.
     */
    public Result<CommentedRant> getRant(int id) {
        return requestHandler.get(ApiEndpoint.RANTS.toString() + '/' + id, CommentedRantResponse.class);
    }

    /**
     * Get a user by username.
     *
     * @param username The username of the user.
     * @return The user.
     */
    public Result<User> getUser(String username) {
        Result<Integer> id = requestHandler.get(ApiEndpoint.USER_ID, UserIdResponse.class, new BasicNameValuePair("username", username));

        // Check the result.
        if (id.getError().isPresent() || !id.getValue().isPresent()) {
            // When the username is invalid, no error message is returned by the API.
            return new Result<>("Invalid username specified.");
        }

        return getUser(id.getValue().get());
    }

    /**
     * Get a user.
     *
     * @param id The id of the user.
     * @return The user.
     */
    public Result<User> getUser(int id) {
        Result<User> result = requestHandler.get(ApiEndpoint.USERS.toString() + '/' + id, UserResponse.class);

        // Check the result.
        if (result.getError().isPresent() || !result.getValue().isPresent()) {
            // When the user id is invalid, no error message is returned by the API.
            return new Result<>("Invalid user id specified.");
        }

        // Set the id, as that is not part of the response.
        result.getValue().get().setId(id);

        return result;
    }

    /**
     * Get a random rant.
     *
     * @return A random rant.
     */
    public Result<Rant> getSurprise() {
        return requestHandler.get(ApiEndpoint.SURPRISE, RantResponse.class);
    }

    /**
     * Get a collab.
     *
     * @param id The id of the collab.
     * @return The collab.
     */
    public Result<Collab> getCollab(int id) {
        return requestHandler.get(ApiEndpoint.RANTS.toString() + '/' + id, CollabResponse.class);
    }

    /**
     * Log in to devRant. When a user is already logged in, they will be logged out first.
     * Note that this method will clear the characters from the password array.
     *
     * @param username The username.
     * @param password The password.
     * @return {@code true} if the user was successfully logged in, or {@code false} otherwise.
     */
    public boolean login(String username, char[] password) {
        logout();

        Result<Auth> response = requestHandler.post(ApiEndpoint.AUTH_TOKEN, AuthResponse.class,
                new BasicNameValuePair("username", username),
                new BasicNameValuePair("password", String.valueOf(password))
        );

        // Clear the password.
        for (int i = 0; i < password.length; i++) {
            password[i] = 0;
        }

        response.getValue().ifPresent(r -> auth = r);
        return isLoggedIn();
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
