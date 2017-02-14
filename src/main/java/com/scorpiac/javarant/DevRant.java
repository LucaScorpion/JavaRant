package com.scorpiac.javarant;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DevRant {
    static final String APP_ID = "3";
    static final String PLAT_ID = "3";

    static final String BASE_URL = "https://www.devrant.io";
    static final String AVATARS_URL = "https://avatars.devrant.io";

    static final String USER_URL = "/users";
    static final String RANT_URL = "/rants";
    static final String COLLAB_URL = "/collabs";

    // API endpoints.
    static final String API_URL = "/api";
    static final String API_RANTS_URL = API_URL + "/devrant/rants";
    static final String API_SEARCH_URL = API_URL + "/devrant/search";
    static final String API_SURPRISE_URL = API_RANTS_URL + "/surprise";
    static final String API_USERS_URL = API_URL + "/users";
    static final String API_USER_ID_URL = API_URL + "/get-user-id";
    static final String API_WEEKLY_URL = API_URL + "/devrant/weekly-rants";
    static final String API_COLLABS_URL = API_URL + "/devrant/collabs";
    static final String API_AUTH_TOKEN = API_USERS_URL + "/auth-token";
    static final String API_COMMENT = "/comments";
    static final String API_VOTE = "/vote";

    /**
     * Get a list of rants.
     *
     * @param sort  The sorting method.
     * @param limit How many rants to get.
     * @param skip  How many rants to skip.
     * @return An array of rants.
     */
    public Rant[] rants(Sort sort, int limit, int skip) {
        // Rants url, app id, sort, skip, limit.
        String url = String.format("%1$s?app=%2$s&sort=%3$s&skip=%4$d&limit=%5$d", API_RANTS_URL, APP_ID, sort.toString(), skip, limit);
        JsonObject json = get(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return null;

        return Util.jsonToList(json.get("rants").getAsJsonArray(), elem -> Rant.fromJson(elem.getAsJsonObject())).toArray(new Rant[0]);
    }

    /**
     * Search for rants matching a certain term.
     *
     * @param term The term to search for.
     * @return An array of rants matching the search term.
     */
    public Rant[] search(String term) {
        // Search url, app id, term.
        String url = String.format("%1$s?app=%2$s&term=%3$s", API_SEARCH_URL, APP_ID, term);
        JsonObject json = get(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return null;

        return Util.jsonToList(json.get("results").getAsJsonArray(), elem -> Rant.fromJson(elem.getAsJsonObject())).toArray(new Rant[0]);
    }

    /**
     * Get a random rant with at least 15 +1's.
     *
     * @return A random rant.
     */
    public Rant surprise() {
        // Surprise url, app id.
        String url = String.format("%1$s?app=%2$s", API_SURPRISE_URL, APP_ID);
        JsonObject json = get(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return null;

        return Rant.fromJson(json.get("rant").getAsJsonObject());
    }

    /**
     * Get the weekly rants.
     *
     * @return The weekly rants.
     */
    public Rant[] weekly() {
        // Weekly url, app id.
        String url = String.format("%1$s?app=%2$s", API_WEEKLY_URL, APP_ID);
        JsonObject json = get(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return null;

        return Util.jsonToList(json.get("rants").getAsJsonArray(), elem -> Rant.fromJson(elem.getAsJsonObject())).toArray(new Rant[0]);
    }

    /**
     * Get the collab rants.
     *
     * @return The collab rants.
     */
    public Collab[] collabs() {
        // Collab url, app id.
        String url = String.format("%1$s?app=%2$s&", API_COLLABS_URL, APP_ID);
        JsonObject json = get(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return null;

        return Util.jsonToList(json.get("rants").getAsJsonArray(), elem -> Collab.fromJson(elem.getAsJsonObject())).toArray(new Collab[0]);
    }

    /**
     * Make a GET-request to the devRant server.
     *
     * @param url The url to make the request to.
     * @return A JsonObject containing the response.
     */
    static JsonObject get(String url) {
        return request(url, "GET");
    }

    /**
     * Make a POST-request to the devRant server.
     *
     * @param url The url to make the request to.
     * @return A JsonObject containing the response.
     */
    static JsonObject post(String url) {
        return request(url, "POST");
    }

    /**
     * Make a request to the devRant server.
     *
     * @param url    The url to make the request to.
     * @param method The request method to use (e.g. "GET" or "POST").
     * @return A JsonObject containing the response.
     */
    private static JsonObject request(String url, String method) {
        HttpURLConnection connection;
        InputStream inputStream;

        try {
            // Create the URL and connection, get the input stream.
            connection = (HttpURLConnection) new URL(BASE_URL + url).openConnection();
            connection.setRequestMethod(method);
            inputStream = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        }

        // Parse the response.
        JsonElement json = new JsonParser().parse(new InputStreamReader(inputStream));

        // Close the stream reader, disconnect.
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.disconnect();

        return json.getAsJsonObject();
    }
}
