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
    public static final String BASE_URL = "https://www.devrant.io";
    public static final String USER_URL = BASE_URL + "/users";
    public static final String RANT_URL = BASE_URL + "/rants";
    public static final String API_URL = BASE_URL + "/api/devrant";
    public static final String RANTS_URL = API_URL + "/rants";

    private static final String APP_ID = "3";

    public static Rant[] getRants(Sort sort, int limit, int skip) {
        // Rants url, app id, sort, skip, limit.
        String url = String.format("%1$s?app=%2$s&sort=%3$s&skip=%4$d&limit=%5$d", RANTS_URL, APP_ID, sort.toString(), skip, limit);
        JsonElement json = request(url);

        return json == null ? new Rant[0] : rantArrayFromJson(json.getAsJsonObject());
    }

    private static JsonElement request(String url) {
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
            rants[i] = rantFromJson(rantArray.get(i).getAsJsonObject());

        return rants;
    }

    private static Rant rantFromJson(JsonObject json) {
        return new Rant(
                json.get("id").getAsInt(),
                json.get("user_username").getAsString(),
                json.get("score").getAsInt(),
                json.get("text").getAsString(),
                json.get("attached_image").getAsString(),
                toStringArray(json.get("tags").getAsJsonArray()),
                json.get("num_comments").getAsInt()
        );
    }

    private static String[] toStringArray(JsonArray json) {
        String[] result = new String[json.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = json.get(i).getAsString();
        return result;
    }
}
