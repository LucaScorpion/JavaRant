package com.scorpiac.javarant;

public class Image {
    private String url;
    private int width;
    private int height;

    private Image(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Image && ((Image)obj).getUrl().equals(url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    /**
     * Get the image url.
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
