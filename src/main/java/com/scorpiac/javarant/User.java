package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class User extends MinimalUser {
    @JsonProperty
    private String about;
    @JsonProperty
    private String location;
    @JsonProperty
    private String skills;
    @JsonProperty
    private String github;
    @JsonProperty
    private String website;
    @JsonProperty
    private UserContent content;

    /**
     * Get information about the user.
     *
     * @return About the user.
     */
    public String getAbout() {
        return about;
    }

    /**
     * Get the user's location.
     *
     * @return The location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the user's skills.
     *
     * @return The skills.
     */
    public String getSkills() {
        return skills;
    }

    /**
     * Get the user's GitHub username.
     *
     * @return The GitHub username.
     */
    public String getGithub() {
        return github;
    }

    /**
     * Get the user's website.
     *
     * @return The website.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Get the rants that this user posted.
     *
     * @return The posted rants.
     */
    public List<CommentedRant> getRants() {
        return Collections.unmodifiableList(content.content.rants);
    }

    /**
     * Get the rants that this user upvoted.
     *
     * @return The upvoted rants.
     */
    public List<CommentedRant> getUpvoted() {
        return Collections.unmodifiableList(content.content.upvoted);
    }

    /**
     * Get this user's comments.
     *
     * @return The posted comments.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(content.content.comments);
    }

    /**
     * Get this user's favorites.
     *
     * @return The favorite rants.
     */
    public List<CommentedRant> getFavorites() {
        return Collections.unmodifiableList(content.content.favorites);
    }

    /**
     * Get the amount of rants that this user has posted.
     *
     * @return The amount of posted rants.
     */
    public int getRantsCount() {
        return content.counts.rants;
    }

    /**
     * Get the amount of rants that this user has upvoted.
     *
     * @return The amount of upvoted rants.
     */
    public int getUpvotedCount() {
        return content.counts.upvoted;
    }

    /**
     * Get the amount of comments that this user has posted.
     *
     * @return The amount of posted comments.
     */
    public int getCommentsCount() {
        return content.counts.comments;
    }

    /**
     * Get the amount of rants that this user has favorited.
     *
     * @return The amount of favorite rants.
     */
    public int getFavoritesCount() {
        return content.counts.favorites;
    }

    /**
     * Get the amount of collabs that this user has posted.
     *
     * @return The amount of posted collabs.
     */
    public int getCollabsCount() {
        return content.counts.collabs;
    }

    private static class UserContent {
        @JsonProperty
        private Content content;
        @JsonProperty
        private Counts counts;
    }

    private static class Content {
        @JsonProperty
        private List<CommentedRant> rants;
        @JsonProperty
        private List<CommentedRant> upvoted;
        @JsonProperty
        private List<Comment> comments;
        @JsonProperty
        private List<CommentedRant> favorites;
    }

    private static class Counts {
        @JsonProperty
        private int rants;
        @JsonProperty
        private int upvoted;
        @JsonProperty
        private int comments;
        @JsonProperty
        private int favorites;
        @JsonProperty
        private int collabs;
    }
}
