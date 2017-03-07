package com.scorpiac.javarant;

import com.google.gson.JsonObject;

public class News {
    private String headline;
    private String body;
    private String footer;

    private News(String headline, String body, String footer) {
        this.headline = headline;
        this.body = body;
        this.footer = footer;
    }

    static News fromJson(JsonObject json) {
        return new News(
                json.has("headline") ? json.get("headline").getAsString() : "",
                json.has("body") ? json.get("body").getAsString() : "",
                json.has("footer") ? json.get("footer").getAsString() : ""
        );
    }

    @Override
    public String toString() {
        return headline + '\n' + body + '\n' + footer;
    }

    /**
     * Get the headline.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Get the body text.
     */
    public String getBody() {
        return body;
    }

    /**
     * Get the footer.
     */
    public String getFooter() {
        return footer;
    }
}
