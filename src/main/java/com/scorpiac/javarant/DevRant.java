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
    static final String API_URL = BASE_URL + "/api/devrant";
    static final String RANTS_URL = API_URL + "/rants";

    public static Rant[] getRants(Sort sort, int limit, int skip) {
        // Rants url, app id, sort, skip, limit.
        String url = String.format("%1$s?app=%2$s&sort=%3$s&skip=%4$d&limit=%5$d", RANTS_URL, APP_ID, sort.toString(), skip, limit);
        JsonElement json = request(url);

        return json == null ? new Rant[0] : rantArrayFromJson(json.getAsJsonObject());
    }

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

    private static Rant[] rantArrayFromJson(JsonObject json) {
        // Get the rant array.
        JsonArray rantArray = json.getAsJsonArray("rants");
        Rant[] rants = new Rant[rantArray.size()];

        for (int i = 0; i < rants.length; i++)
            rants[i] = Rant.fromJson(rantArray.get(i).getAsJsonObject());

        return rants;
    }
}
