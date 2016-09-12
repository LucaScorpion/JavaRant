package com.scorpiac.javarant;

public abstract class RantContent {
    private String author;
    private int score;
    private String content;

    public RantContent(String author, int score, String content) {
        this.author = author;
        this.score = score;
        this.content = content;
    }

    /**
     * Get the link to the author's profile.
     */
    public String authorLink() {
        return DevRant.USER_URL + author;
    }

    /**
     * Get the author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the content (text).
     */
    public String getContent() {
        return content;
    }
}
