package com.scorpiac.javarant;

public abstract class RantContent {
    private int id;
    private User user;
    private int score;
    private VoteState voteState = VoteState.NONE;
    protected String text;
    private Image image;

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RantContent && ((RantContent) obj).getId() == id;
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
     * Get the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the vote state.
     */
    public VoteState getVoteState() {
        return voteState;
    }

    /**
     * Get the text.
     */
    public String getText() {
        return text;
    }

    /**
     * Get the image, or {@code null} if there is none.
     */
    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return text;
    }
}
