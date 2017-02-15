package com.scorpiac.javarant;

import com.google.gson.JsonObject;
import com.scorpiac.javarant.exceptions.NoSuchUserException;

import java.util.Collections;
import java.util.List;

public class User extends DevRantHolder {
    // Data that is always available.
    private int id;
    private String username;
    private int score;

    // Data that need to be fetched from the user profile.
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

    private User(DevRant devRant) {
        super(devRant);
    }

    User(DevRant devRant, int id) {
        super(devRant);
        this.id = id;

        // Fetch the data, check if the user exists.
        if (!fetchData())
            throw new NoSuchUserException(id);
    }

    /**
     * Create a user from a JSON object.
     *
     * @param devRant The devRant client to use.
     * @param json    The JSON object to create the user from.
     * @return The created user.
     */
    static User fromJson(DevRant devRant, JsonObject json) {
        User result = new User(devRant);

        result.id = json.get("user_id").getAsInt();
        result.username = json.get("user_username").getAsString();
        result.score = json.get("user_score").getAsInt();

        return result;
    }

    /**
     * Fetch the user data from the user profile. If the data is already fetched, it will not be fetched again.
     *
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchData() {
        return fetchData(false);
    }

    /**
     * Fetch the user data from the user profile.
     *
     * @param force Whether to fetch the data even if it was already fetched.
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchData(boolean force) {
        // Check if we already fetched and force is false.
        if (fetched && !force)
            return true;
        
        JsonObject json = devRant.get(DevRant.API_USERS + '/' + id);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return false;
        fetched = true;

        // JSON objects.
        JsonObject profileJson = json.getAsJsonObject("profile");
        JsonObject contentJson = profileJson.getAsJsonObject("content");
        JsonObject subContentJson = contentJson.getAsJsonObject("content");
        JsonObject countsJson = contentJson.getAsJsonObject("counts");
        JsonObject avatarJson = profileJson.getAsJsonObject("avatar");

        // Set all the fields.
        username = profileJson.get("username").getAsString();
        score = profileJson.get("score").getAsInt();
        avatar = avatarJson.get("i").getAsString();

        about = profileJson.get("about").getAsString();
        location = profileJson.get("location").getAsString();
        skills = profileJson.get("skills").getAsString();
        github = profileJson.get("github").getAsString();

        rantsCount = countsJson.get("rants").getAsInt();
        upvotedCount = countsJson.get("upvoted").getAsInt();
        commentsCount = countsJson.get("comments").getAsInt();
        favoritesCount = countsJson.get("favorites").getAsInt();

        rants = Util.jsonToList(subContentJson.get("rants").getAsJsonArray(), rant -> Rant.fromJson(devRant, rant.getAsJsonObject()));
        upvoted = Util.jsonToList(subContentJson.get("upvoted").getAsJsonArray(), rant -> Rant.fromJson(devRant, rant.getAsJsonObject()));
        comments = Util.jsonToList(subContentJson.get("comments").getAsJsonArray(), comment -> Comment.fromJson(devRant, comment.getAsJsonObject()));
        favorites = Util.jsonToList(subContentJson.get("favorites").getAsJsonArray(), rant -> Rant.fromJson(devRant, rant.getAsJsonObject()));

        return true;
    }

    /**
     * Get whether the user data is fetched.
     */
    public boolean isFetched() {
        return fetched;
    }

    /**
     * Get the link to the user.
     */
    public String userLink() {
        return DevRant.BASE_URL + DevRant.USER_URL + '/' + username;
    }

    /**
     * Get the link to the user's avatar.
     */
    public String avatarLink() {
        fetchData();
        return DevRant.AVATARS_URL + "/" + avatar;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && ((User) obj).getId() == id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Get the user id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the user's overall score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get information about the user.
     */
    public String getAbout() {
        fetchData();
        return about;
    }

    /**
     * Get the user's location.
     */
    public String getLocation() {
        fetchData();
        return location;
    }

    /**
     * Get the user's skills.
     */
    public String getSkills() {
        fetchData();
        return skills;
    }

    /**
     * Get the user's GitHub username.
     */
    public String getGithub() {
        fetchData();
        return github;
    }

    /**
     * Get the rants that this user posted.
     */
    public List<Rant> getRants() {
        fetchData();
        return Collections.unmodifiableList(rants);
    }

    /**
     * Get the rants that this user upvoted.
     */
    public List<Rant> getUpvoted() {
        fetchData();
        return Collections.unmodifiableList(upvoted);
    }

    /**
     * Get this user's comments.
     */
    public List<Comment> getComments() {
        fetchData();
        return Collections.unmodifiableList(comments);
    }

    /**
     * Get this user's favorites.
     */
    public List<Rant> getFavorites() {
        fetchData();
        return Collections.unmodifiableList(favorites);
    }

    /**
     * Get the amount of rants that this user has posted.
     */
    public int getRantsCount() {
        fetchData();
        return rantsCount;
    }

    /**
     * Get the amount of rants that this user has upvoted.
     */
    public int getUpvotedCount() {
        fetchData();
        return upvotedCount;
    }

    /**
     * Get the amount of comments that this user has posted.
     */
    public int getCommentsCount() {
        fetchData();
        return commentsCount;
    }

    /**
     * Get the amount of rants that this user has favorited.
     */
    public int getFavoritesCount() {
        fetchData();
        return favoritesCount;
    }

    @Override
    public String toString() {
        return username;
    }
}
