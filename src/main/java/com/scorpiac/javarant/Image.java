package com.scorpiac.javarant;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Image {
    private String url;
    private int width;
    private int height;

    private Image(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    /**
     * Get the image from the JSON, or {@code null} if there is no image.
     *
     * @param json The JSON element for the image.
     * @return The image, or {@code null}.
     */
    static Image fromJson(JsonElement json) {
        if (json == null || !json.isJsonObject())
            return null;

        JsonObject obj = json.getAsJsonObject();
        return new Image(obj.get("url").getAsString(), obj.get("width").getAsInt(), obj.get("height").getAsInt());
    }

    /**
     * Get the image url.
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the image width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the image height.
     */
    public int getHeight() {
        return height;
    }
}
