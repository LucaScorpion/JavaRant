package com.scorpiac.javarant;

public abstract class RantContent {
    private final int id;
    private final User user;
    private int upvotes;
    private int downvotes;
    private String content;

    public RantContent(int id, User user, int upvotes, int downvotes, String content) {
        this.id = id;
        this.user = user;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.content = content;
    }

    /**
     * Get the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the author.
     */
    public User getUser() {
        return user;
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

    @Override
    public String toString() {
        return content;
    }
}
