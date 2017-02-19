package com.scorpiac.javarant;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.scorpiac.javarant.exceptions.AuthenticationException;
import com.scorpiac.javarant.exceptions.NoSuchRantException;
import com.scorpiac.javarant.exceptions.NoSuchUserException;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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

    /**
     * Log in to devRant.
     *
     * @param username The username.
     * @param password The password.
     * @throws AuthenticationException If the login data is invalid.
     */
    public void login(String username, char[] password) throws AuthenticationException {
        if (auth != null)
            throw new IllegalStateException("A user is already logged in.");

        JsonObject json = post(API_AUTH_TOKEN,
                new BasicNameValuePair("username", username),
                new BasicNameValuePair("password", String.valueOf(password))
        );

        // Clear the password.
        for (int i = 0; i < password.length; i++)
            password[i] = 0;

        if (!Util.jsonSuccess(json))
            throw new AuthenticationException();

        auth = Auth.fromJson(json);
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
     * Get a list of rants.
     *
     * @param sort  The sorting method.
     * @param limit How many rants to get.
     * @param skip  How many rants to skip.
     * @return An list of rants.
     */
    public List<Rant> getRants(Sort sort, int limit, int skip) {
        return getFeed(API_RANTS, elem -> Rant.fromJson(this, elem), sort, limit, skip);
    }

    /**
     * Search for rants matching a certain term.
     *
     * @param term The term to search for.
     * @return A list of rants matching the search term.
     */
    public List<Rant> search(String term) {
        return getFeed(API_SEARCH, elem -> Rant.fromJson(this, elem), new BasicNameValuePair("term", term));
    }

    /**
     * Get a random rant with at least 15 ++'s.
     *
     * @return A random rant.
     */
    public Rant getSurprise() {
        JsonObject json = get(API_SURPRISE);
        return Util.jsonSuccess(json) ? Rant.fromJson(this, json.get("rant").getAsJsonObject()) : null;
    }

    /**
     * Get the weekly rants.
     *
     * @param sort  The sorting method.
     * @param limit How many rants to get.
     * @param skip  How many rants to skip.
     * @return The weekly rants.
     */
    public List<Rant> getWeekly(Sort sort, int limit, int skip) {
        return getFeed(API_WEEKLY, elem -> Rant.fromJson(this, elem), sort, limit, skip);
    }

    /**
     * Get the collabs.
     *
     * @param limit How many rants to get.
     * @param skip  How many rants to skip.
     * @return A list of collabs.
     */
    public List<Collab> getCollabs(int limit, int skip) {
        return getFeed(API_COLLABS, elem -> Collab.fromJson(this, elem.getAsJsonObject()),
                new BasicNameValuePair("limit", String.valueOf(limit)),
                new BasicNameValuePair("skip", String.valueOf(skip))
        );
    }

    /**
     * Get the story rants.
     *
     * @param sort  The sorting method.
     * @param limit How many rants to get.
     * @param skip  How many rants to skip.
     * @return A list of story rants.
     */
    public List<Rant> getStories(Sort sort, int limit, int skip) {
        return getFeed(API_STORIES, elem -> Rant.fromJson(this, elem), sort, limit, skip);
    }

    private <T> List<T> getFeed(String url, Function<JsonObject, T> converter, Sort sort, int limit, int skip) {
        return getFeed(url, converter,
                new BasicNameValuePair("sort", sort.toString()),
                new BasicNameValuePair("limit", String.valueOf(limit)),
                new BasicNameValuePair("skip", String.valueOf(skip)),
                sort.getParameter()
        );
    }

    private <T> List<T> getFeed(String url, Function<JsonObject, T> converter, NameValuePair... params) {
        JsonObject json = get(url, params);
        return Util.jsonSuccess(json) ? Util.jsonToList(json.get("rants").getAsJsonArray(), obj -> converter.apply(obj.getAsJsonObject())) : null;
    }

    /**
     * Get a rant by its id.
     *
     * @param id The id of the rant to get.
     * @return The rant.
     */
    public Rant getRant(int id) {
        JsonObject json = get(API_RANTS + '/' + id);

        // Check if the rant exists.
        if (!Util.jsonSuccess(json))
            throw new NoSuchRantException(id);

        return Rant.fromJson(this, json.get("rant").getAsJsonObject(), json.get("comments").getAsJsonArray());
    }

    /**
     * Get a collab by its id.
     *
     * @param id The id of the collab to get.
     * @return The collab.
     */
    public Collab getCollab(int id) {
        JsonObject json = get(API_RANTS + '/' + id);

        // Check if the collab exists.
        if (!Util.jsonSuccess(json))
            throw new NoSuchRantException(id);

        return Collab.fromJson(this, json.get("rant").getAsJsonObject(), json.get("comments").getAsJsonArray());
    }

    /**
     * Get a user by their username.
     *
     * @param username The username of the user to get.
     * @return The user.
     */
    public User getUser(String username) {
        JsonObject json = get(API_USER_ID, new BasicNameValuePair("username", username));

        // Check if the user exists.
        if (!Util.jsonSuccess(json))
            throw new NoSuchUserException(username);

        return getUser(json.get("user_id").getAsInt());
    }

    /**
     * Get a user by their id.
     *
     * @param id The id of the user to get.
     * @return The user.
     */
    public User getUser(int id) {
        return new User(this, id);
    }

    /**
     * Vote on a rant.
     *
     * @param rant The rant to vote on.
     * @param vote The vote.
     * @return Whether the vote was successful.
     */
    public boolean vote(Rant rant, Vote vote) {
        return voteRant(rant.getId(), vote);
    }

    /**
     * Vote on a rant.
     *
     * @param id   The id of the rant.
     * @param vote The vote.
     * @return Whether the vote was successful.
     */
    public boolean voteRant(int id, Vote vote) {
        // Rants url, id, vote url.
        String url = String.format("%1$s/%2$d%3$s", API_RANTS, id, API_VOTE);
        return Util.jsonSuccess(post(url, new BasicNameValuePair("vote", vote.toString()), vote.getParameter()));
    }

    /**
     * Vote on a comment.
     *
     * @param comment The comment to vote on.
     * @param vote    The vote.
     * @return Whether the vote was successful.
     */
    public boolean vote(Comment comment, Vote vote) {
        return voteComment(comment.getId(), vote);
    }

    /**
     * Vote on a comment.
     *
     * @param id   The id of the comment.
     * @param vote The vote.
     * @return Whether the vote was successful.
     */
    public boolean voteComment(int id, Vote vote) {
        // API url, comments url, id, vote url.
        String url = String.format("%1$s%2$s/%3$d%4$s", API, API_COMMENT, id, API_VOTE);
        return Util.jsonSuccess(post(url, new BasicNameValuePair("vote", String.valueOf(vote.toString())), vote.getParameter()));
    }

    /**
     * Post a rant.
     *
     * @param rant The content of the rant.
     * @param tags The tags.
     * @return Whether posting the rant was successful.
     */
    public boolean postRant(String rant, String tags) {
        return Util.jsonSuccess(post(API_RANTS,
                new BasicNameValuePair("rant", rant),
                new BasicNameValuePair("tags", tags)
        ));
    }

    /**
     * Post a comment.
     *
     * @param rant    The rant to post the comment on.
     * @param comment The content of the comment.
     * @return Whether posting the comment was successful.
     */
    public boolean postComment(Rant rant, String comment) {
        return postComment(rant.getId(), comment);
    }

    /**
     * Post a comment.
     *
     * @param rantId  The id of the rant to post the comment on.
     * @param comment The content of the comment.
     * @return Whether posting the comment was successful.
     */
    public boolean postComment(int rantId, String comment) {
        // Rants url, rant, comments url.
        String url = String.format("%1$s/%2$d%3$s", API_RANTS, rantId, API_COMMENT);
        return Util.jsonSuccess(post(url, new BasicNameValuePair("comment", comment)));
    }

    /**
     * Make a POST-request to the devRant server.
     *
     * @param url    The url to make the request to.
     * @param params The parameters to post.
     * @return A {@link JsonObject} containing the response.
     */
    JsonObject post(String url, NameValuePair... params) {
        List<NameValuePair> paramList = getParameters(params);
        return executeRequest(Request.Post(BASE_URL + url).bodyForm(paramList));
    }

    /**
     * Make a GET-request to the devRant server.
     *
     * @param url The url to make the request to.
     * @return A {@link JsonObject} containing the response.
     */
    JsonObject get(String url, NameValuePair... params) {
        StringBuilder finalUrl = new StringBuilder(url).append('?');
        List<NameValuePair> paramList = getParameters(params);

        // Add all parameters.
        try {
            for (NameValuePair param : paramList)
                finalUrl.append('&').append(param.getName()).append('=').append(URLEncoder.encode(param.getValue(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // This never happens.
            LOGGER.error("Unsupported encoding while trying to encode parameter value.", e);
        }

        return executeRequest(Request.Get(BASE_URL + finalUrl.toString()));
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
     * Execute a request and parse the response.
     *
     * @param request The request to execute.
     * @return A {@link JsonObject} containing the response.
     */
    private JsonObject executeRequest(Request request) {
        // Make the request and get the returned content as a stream.
        InputStream stream;
        try {
            stream = request.socketTimeout(timeout).connectTimeout(timeout).execute().returnContent().asStream();
        } catch (IOException e) {
            LOGGER.warn("Exception while processing request:\n" + request.toString() + '\n' + e.getMessage());
            return null;
        }

        // Parse the response as json.
        try (JsonReader reader = new JsonReader(new InputStreamReader(stream))) {
            return new JsonParser().parse(reader).getAsJsonObject();
        } catch (IOException e) {
            LOGGER.error("Exception while trying to create JsonReader.", e);
            return null;
        }
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
}
