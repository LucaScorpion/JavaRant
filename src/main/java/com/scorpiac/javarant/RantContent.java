package com.scorpiac.javarant;

public abstract class RantContent {
    private String author;
    private int upvotes;
    private int downvotes;
    private String content;

    public RantContent(String author, int upvotes, int downvotes, String content) {
        this.author = author;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
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
     * Get the amount of upvotes.
     */
    public int getUpvotes() {
        return upvotes;
    }

    /**
     * Get the amount of downvotes.
     */
    public int getDownvotes() {
        return downvotes;
    }

    /**
     * Get the score.
     */
    public int getScore() {
        return upvotes - downvotes;
    }

    /**
     * Get the content (text).
     */
    public String getContent() {
        return content;
    }
}
