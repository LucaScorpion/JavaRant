package com.scorpiac.javarant;

public class News {
    private String headline;
    private String body;
    private String footer;

    private News(String headline, String body, String footer) {
        this.headline = headline;
        this.body = body;
        this.footer = footer;
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
