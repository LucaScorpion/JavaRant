package com.scorpiac.javarant;

import com.scorpiac.javarant.services.RequestHandler;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public class User extends MinimalUser {
    private boolean fetched = false;
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
     * Get whether the user data is fetched.
     */
    public boolean isFetched() {
        return fetched;
    }

    /**
     * Get the link to the user's avatar.
     */
    public URI avatarLink() {
        return RequestHandler.AVATARS_URI.resolve(avatar);
    }

    /**
     * Get information about the user.
     */
    public String getAbout() {
        return about;
    }

    /**
     * Get the user's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the user's skills.
     */
    public String getSkills() {
        return skills;
    }

    /**
     * Get the user's GitHub username.
     */
    public String getGithub() {
        return github;
    }

    /**
     * Get the rants that this user posted.
     */
    public List<Rant> getRants() {
        return Collections.unmodifiableList(rants);
    }

    /**
     * Get the rants that this user upvoted.
     */
    public List<Rant> getUpvoted() {
        return Collections.unmodifiableList(upvoted);
    }

    /**
     * Get this user's comments.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    /**
     * Get this user's favorites.
     */
    public List<Rant> getFavorites() {
        return Collections.unmodifiableList(favorites);
    }

    /**
     * Get the amount of rants that this user has posted.
     */
    public int getRantsCount() {
        return rantsCount;
    }

    /**
     * Get the amount of rants that this user has upvoted.
     */
    public int getUpvotedCount() {
        return upvotedCount;
    }

    /**
     * Get the amount of comments that this user has posted.
     */
    public int getCommentsCount() {
        return commentsCount;
    }

    /**
     * Get the amount of rants that this user has favorited.
     */
    public int getFavoritesCount() {
        return favoritesCount;
    }
}
