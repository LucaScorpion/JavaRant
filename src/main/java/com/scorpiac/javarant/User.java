package com.scorpiac.javarant;

import com.google.gson.JsonObject;
import com.scorpiac.javarant.exceptions.NoSuchUserException;

public class User {
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
    private Rant[] rants;
    private Rant[] upvoted;
    private Comment[] comments;
    private Rant[] favorites;
    private int rantsCount;
    private int upvotedCount;
    private int commentsCount;
    private int favoritesCount;
    private String avatar;

    /**
     * Create a new user.
     *
     * @param id The id of the user.
     */
    private User(int id) {
        this.id = id;
    }

    /**
     * Create a new user.
     *
     * @param id       The id of the user.
     * @param username The username of the user.
     * @param score    The score of the user.
     */
    private User(int id, String username, int score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }

    /**
     * Get a user by their id. This will also fetch all the user data.
     *
     * @param id The id of the user to get.
     * @return The user.
     */
    public static User byId(int id) {
        User user = new User(id);

        // Check if the user exists.
        if (!user.fetchData())
            throw new NoSuchUserException(id);

        return user;
    }

    /**
     * Get a user by their username. This will also fetch all the user data.
     *
     * @param username The username of the user to get.
     * @return The user.
     */
    public static User byUsername(String username) {
        // Users url, user id, app id.
        String url = String.format("%1$s?app=%2$s&username=%3$s", DevRant.API_USER_ID_URL, DevRant.APP_ID, username);
        JsonObject json = DevRant.request(url);

        // Check if the user exists.
        if (!Util.jsonSuccess(json))
            throw new NoSuchUserException(username);

        return byId(json.get("user_id").getAsInt());
    }

    /**
     * Create a user from a JSON object.
     *
     * @param json The JSON object to create the user from.
     * @return The created user.
     */
    static User fromJson(JsonObject json) {
        return new User(
                json.get("user_id").getAsInt(),
                json.get("user_username").getAsString(),
                json.get("user_score").getAsInt()
        );
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

        // Users url, user id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_USERS_URL, id, DevRant.APP_ID);
        JsonObject json = DevRant.request(url);

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

        rants = Util.jsonToList(subContentJson.get("rants").getAsJsonArray(), rant -> Rant.fromJson(rant.getAsJsonObject())).toArray(new Rant[0]);
        upvoted = Util.jsonToList(subContentJson.get("upvoted").getAsJsonArray(), rant -> Rant.fromJson(rant.getAsJsonObject())).toArray(new Rant[0]);
        comments = Util.jsonToList(subContentJson.get("comments").getAsJsonArray(), comment -> Comment.fromJson(comment.getAsJsonObject())).toArray(new Comment[0]);
        favorites = Util.jsonToList(subContentJson.get("favorites").getAsJsonArray(), rant -> Rant.fromJson(rant.getAsJsonObject())).toArray(new Rant[0]);

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
        return DevRant.link(DevRant.USER_URL + "/" + username);
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
    public Rant[] getRants() {
        fetchData();
        return rants;
    }

    /**
     * Get the rants that this user upvoted.
     */
    public Rant[] getUpvoted() {
        fetchData();
        return upvoted;
    }

    /**
     * Get this user's comments.
     */
    public Comment[] getComments() {
        fetchData();
        return comments;
    }

    /**
     * Get this user's favorites.
     */
    public Rant[] getFavorites() {
        fetchData();
        return favorites;
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
