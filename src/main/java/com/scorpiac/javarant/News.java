package com.scorpiac.javarant;

public class News {
    private String headline;
    private String body;
    private String footer;

    @Override
    public String toString() {
        return headline + '\n' + body + '\n' + footer;
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
