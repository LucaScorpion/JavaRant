package com.scorpiac.javarant;

public class Image {
    private String url;
    private int width;
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
