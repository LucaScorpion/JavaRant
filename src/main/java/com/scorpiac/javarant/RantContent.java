package com.scorpiac.javarant;

public abstract class RantContent extends DevRantHolder {
    private final int id;
    private final User user;
    private int upvotes;
    private int downvotes;
    private int score;
    private VoteState voteState;
    private String content;
    private Image image;

    protected RantContent(DevRant devRant, int id, User user, int upvotes, int downvotes, int score, int voteState, String content, Image image) {
        super(devRant);
        this.id = id;
        this.user = user;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.score = score;
        this.voteState = VoteState.fromValue(voteState);
        this.content = content;
        this.image = image;
    }

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
        return score;
    }

    /**
     * Get the vote state.
     */
    public VoteState getVoteState() {
        return voteState;
    }

    /**
     * Get the content (text).
     */
    public String getContent() {
        return content;
    }

    /**
     * Get the image, or {@code null} if there is none.
     */
    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return content;
    }
}
