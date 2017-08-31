package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RantContent {
    private int id;
    private int score;
    private VoteState voteState = VoteState.NONE;
    protected String text;
    @JsonProperty("attached_image")
    private Image image;

    // Minimal user info.
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_username")
    private String username;
    @JsonProperty("user_score")
    private int userScore;

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
     *
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the user.
     *
     * @return The user.
     */
    public MinimalUser getUser() {
        return MinimalUser.create(userId, username, userScore);
    }

    /**
     * Get the score.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the vote state of the authenticated user.
     * If no user is logged in, the vote state is always {@link VoteState#NONE}.
     *
     * @return The vote state.
     */
    public VoteState getVoteState() {
        return voteState;
    }

    /**
     * Get the text.
     *
     * @return The text.
     */
    public String getText() {
        return text;
    }

    /**
     * Get the image, or {@code null} if there is none.
     *
     * @return The image.
     */
    public Image getImage() {
        return image;
    }
}
