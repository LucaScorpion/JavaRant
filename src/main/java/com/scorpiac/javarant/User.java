package com.scorpiac.javarant;

import com.scorpiac.javarant.services.RequestHandler;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public class User extends MinimalUser {
    private String about;
    private String location;
    private String skills;
    private String github;
    private List<Rant> rants;
    private List<Rant> upvoted;
    private List<Comment> comments;
    private List<Rant> favorites;
    private int rantsCount;
    private int upvotedCount;
    private int commentsCount;
    private int favoritesCount;
    private String avatar;

    /**
     * Get the link to the user's avatar.
     *
     * @return A link to the avatar.
     */
    public URI avatarLink() {
        return RequestHandler.AVATARS_URI.resolve(avatar);
    }

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
     * Get the rants that this user posted.
     *
     * @return The posted rants.
     */
    public List<Rant> getRants() {
        return Collections.unmodifiableList(rants);
    }

    /**
     * Get the rants that this user upvoted.
     *
     * @return The upvoted rants.
     */
    public List<Rant> getUpvoted() {
        return Collections.unmodifiableList(upvoted);
    }

    /**
     * Get this user's comments.
     *
     * @return The posted comments.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    /**
     * Get this user's favorites.
     *
     * @return The favorite rants.
     */
    public List<Rant> getFavorites() {
        return Collections.unmodifiableList(favorites);
    }

    /**
     * Get the amount of rants that this user has posted.
     *
     * @return The amount of posted rants.
     */
    public int getRantsCount() {
        return rantsCount;
    }

    /**
     * Get the amount of rants that this user has upvoted.
     *
     * @return The amount of upvoted rants.
     */
    public int getUpvotedCount() {
        return upvotedCount;
    }

    /**
     * Get the amount of comments that this user has posted.
     *
     * @return The amount of posted comments.
     */
    public int getCommentsCount() {
        return commentsCount;
    }

    /**
     * Get the amount of rants that this user has favorited.
     *
     * @return The amount of favorite rants.
     */
    public int getFavoritesCount() {
        return favoritesCount;
    }
}
