package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

public class Image {
    @JsonProperty("url")
    private URI link;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Image && ((Image) obj).getLink().equals(link);
    }

    @Override
    public int hashCode() {
        return link.hashCode();
    }

    /**
     * Get the image link.
     *
     * @return The image link.
     */
    public URI getLink() {
        return link;
    }

    /**
     * Get the image width in pixels.
     *
     * @return The image width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the image height in pixels.
     *
     * @return The image height.
     */
    public int getHeight() {
        return height;
    }
}
