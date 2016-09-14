package com.scorpiac.javarant;

import com.google.gson.JsonArray;
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
    static final String BASE_URL = "https://www.devrant.io";
    static final String USER_URL = BASE_URL + "/users";
    static final String RANT_URL = BASE_URL + "/rants";
    static final String API_URL = BASE_URL + "/api";
    static final String API_RANTS_URL = API_URL + "/devrant/rants";
    static final String API_SURPRISE_URL = API_RANTS_URL + "/surprise";
    static final String API_USERS_URL = API_URL + "/users";

    /**
     * Get a list of rants.
     * @param sort The sorting method.
     * @param limit How many rants to get.
     * @param skip How many rants to skip.
     * @return An array of rants.
     */
    public static Rant[] getRants(Sort sort, int limit, int skip) {
        // Rants url, app id, sort, skip, limit.
        String url = String.format("%1$s?app=%2$s&sort=%3$s&skip=%4$d&limit=%5$d", API_RANTS_URL, APP_ID, sort.toString(), skip, limit);
        JsonArray rantsJson = request(url).getAsJsonObject().get("rants").getAsJsonArray();
        return Util.jsonToList(rantsJson, elem -> Rant.fromJson(elem.getAsJsonObject())).toArray(new Rant[0]);
    }

    /**
     * Get a random rant with at leatst 15 +1's.
     * @return A random rant.
     */
    public static Rant surprise() {
        // Surprise url, app id.
        String url = String.format("%1$s?app=%2$s", API_SURPRISE_URL, APP_ID);
        JsonObject rantJson = request(url).getAsJsonObject().get("rant").getAsJsonObject();
        return Rant.fromJson(rantJson);
    }

    /**
     * Make a request to the DevRant server.
     * @param url The complete url to make the request to.
     * @return A JsonElement containing the response.
     */
    static JsonElement request(String url) {
        HttpURLConnection connection;
        InputStream inputStream;

        try {
            // Create the URL and connection, get the input stream.
            connection = (HttpURLConnection) new URL(url).openConnection();
            inputStream = connection.getInputStream();
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

        return json;
    }
}
