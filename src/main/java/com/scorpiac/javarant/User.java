package com.scorpiac.javarant;

import com.google.gson.JsonObject;

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

    private User(int id) {
        this.id = id;
        fetchData();
    }

    public User(int id, String username, int score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }

    public static User byId(int id) {
        return new User(id);
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
     */
    public void fetchData() {
        // Check if we already fetched the data.
        if (fetched)
            return;
        fetched = true;

        // Users url, user id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_USERS_URL, id, DevRant.APP_ID);
        JsonObject userJson = DevRant.request(url).getAsJsonObject().get("profile").getAsJsonObject();
        JsonObject contentJson = userJson.get("content").getAsJsonObject();
        JsonObject subContentJson = contentJson.get("content").getAsJsonObject();
        JsonObject countsJson = contentJson.get("counts").getAsJsonObject();

        // Set all the fields.
        username = userJson.get("username").getAsString();
        score = userJson.get("score").getAsInt();

        about = userJson.get("about").getAsString();
        location = userJson.get("location").getAsString();
        skills = userJson.get("skills").getAsString();
        github = userJson.get("github").getAsString();

        rantsCount = countsJson.get("rants").getAsInt();
        upvotedCount = countsJson.get("upvoted").getAsInt();
        commentsCount = countsJson.get("comments").getAsInt();
        favoritesCount = countsJson.get("favorites").getAsInt();

        rants = Util.jsonToList(subContentJson.get("rants").getAsJsonArray(), rant -> Rant.fromJson(rant.getAsJsonObject())).toArray(new Rant[0]);
        upvoted = Util.jsonToList(subContentJson.get("upvoted").getAsJsonArray(), rant -> Rant.fromJson(rant.getAsJsonObject())).toArray(new Rant[0]);
        comments = Util.jsonToList(subContentJson.get("comments").getAsJsonArray(), comment -> Comment.fromJson(comment.getAsJsonObject())).toArray(new Comment[0]);
        favorites = Util.jsonToList(subContentJson.get("favorites").getAsJsonArray(), rant -> Rant.fromJson(rant.getAsJsonObject())).toArray(new Rant[0]);
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
