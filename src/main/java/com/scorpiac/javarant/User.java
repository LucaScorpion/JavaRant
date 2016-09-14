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

    static User fromJson(JsonObject json) {
        return new User(
                json.get("user_id").getAsInt(),
                json.get("user_username").getAsString(),
                json.get("user_score").getAsInt()
        );
    }

    /**
     * Fetch the user data from the user profile.
     *
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchData() {
        // Users url, user id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_USERS_URL, id, DevRant.APP_ID);
        JsonObject json = DevRant.request(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return false;
        fetched = true;

        // JSON objects.
        JsonObject profileJson = json.get("profile").getAsJsonObject();
        JsonObject contentJson = profileJson.get("content").getAsJsonObject();
        JsonObject subContentJson = contentJson.get("content").getAsJsonObject();
        JsonObject countsJson = contentJson.get("counts").getAsJsonObject();

        // Set all the fields.
        username = profileJson.get("username").getAsString();
        score = profileJson.get("score").getAsInt();

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

    public String userLink() {
        return DevRant.link(DevRant.USER_URL + "/" + username);
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

    public String getAbout() {
        fetchData();
        return about;
    }

    public String getLocation() {
        fetchData();
        return location;
    }

    public String getSkills() {
        fetchData();
        return skills;
    }

    public String getGithub() {
        fetchData();
        return github;
    }

    public Rant[] getRants() {
        fetchData();
        return rants;
    }

    public Rant[] getUpvoted() {
        fetchData();
        return upvoted;
    }

    public Comment[] getComments() {
        fetchData();
        return comments;
    }

    public Rant[] getFavorites() {
        fetchData();
        return favorites;
    }

    public int getRantsCount() {
        fetchData();
        return rantsCount;
    }

    public int getUpvotedCount() {
        fetchData();
        return upvotedCount;
    }

    public int getCommentsCount() {
        fetchData();
        return commentsCount;
    }

    public int getFavoritesCount() {
        fetchData();
        return favoritesCount;
    }

    @Override
    public String toString() {
        return username;
    }
}
