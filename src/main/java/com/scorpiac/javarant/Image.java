package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Image {
    @JsonProperty("url")
    private String url;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Image && ((Image) obj).getLink().equals(url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    /**
     * Get the image link.
     */
    public String getLink() {
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
