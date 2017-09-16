package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class News {
    @JsonProperty
    private int id;
    @JsonProperty
    private String headline;
    @JsonProperty
    private String body;
    @JsonProperty
    private String footer;

    @Override
    public String toString() {
        return headline + '\n' + body + '\n' + footer;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof News && ((News) obj).id == id;
    }

    /**
     * Get the id.
     *
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the headline.
     *
     * @return The headline.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Get the body text.
     *
     * @return The body.
     */
    public String getBody() {
        return body;
    }

    /**
     * Get the footer.
     *
     * @return The footer.
     */
    public String getFooter() {
        return footer;
    }
}
