package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scorpiac.javarant.exceptions.NoSuchRantException;

public class Collab extends Rant {
    private String projectType;

    // Data that needs to be fetched.
    private boolean fetched = false;
    private String description;
    private String techStack;
    private String teamSize;
    private String url;

    protected Collab(int id, User user, int upvotes, int downvotes, String projectType, String summary, int commentCount) {
        super(id, user, upvotes, downvotes, summary, null, new String[0], commentCount);
        this.projectType = projectType;
    }

    /**
     * Get a collab by its id.
     *
     * @param id The id of the collab to get.
     * @return The collab.
     */
    public static Collab byId(int id) {
        // Collabs url, collab id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_RANTS_URL, id, DevRant.APP_ID);
        JsonObject json = DevRant.request(url);

        // Check if the collab exists.
        if (!Util.jsonSuccess(json))
            throw new NoSuchRantException(id);

        // Get the collab and data.
        JsonObject obj = json.get("rant").getAsJsonObject();
        Collab collab = fromJson(obj);
        collab.setData(obj, json.get("comments").getAsJsonArray());
        return collab;
    }

    static Collab fromJson(JsonObject json) {
        return new Collab(
                json.get("id").getAsInt(),
                User.fromJson(json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("c_type_long").getAsString(),
                json.get("text").getAsString(),
                json.get("num_comments").getAsInt()
        );
    }

    private void setData(JsonObject collab, JsonArray comments) {
        commentsFromJson(comments);
        description = collab.get("c_description").getAsString();
        techStack = collab.get("c_tech_stack").getAsString();
        teamSize = collab.get("c_team_size").getAsString();
        url = collab.get("c_url").getAsString();
    }

    /**
     * Fetch the data for this collab. If the data is already fetched, it will not be fetched again.
     *
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchData() {
        return fetchData(false);
    }

    /**
     * Fetch the data for this collab.
     *
     * @param force Whether to fetch the data even if it was already fetched.
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchData(boolean force) {
        // Check if we already fetched and force is false.
        if (fetched && !force)
            return true;

        // Collabs url, collab id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_RANTS_URL, getId(), DevRant.APP_ID);
        JsonObject json = DevRant.request(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return false;
        fetched = true;

        setData(json.get("rant").getAsJsonObject(), json.get("comments").getAsJsonArray());

        return true;
    }

    /**
     * Get the link to the collab.
     */
    @Override
    public String link() {
        return DevRant.link(DevRant.COLLAB_URL + "/" + getId());
    }

    /**
     * Get the project type.
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * Get the project description.
     */
    public String getDescription() {
        fetchData();
        return description;
    }

    /**
     * Get the project tech stack.
     */
    public String getTechStack() {
        fetchData();
        return techStack;
    }

    /**
     * Get the team size.
     */
    public String getTeamSize() {
        fetchData();
        return teamSize;
    }

    /**
     * Get the project url.
     */
    public String getUrl() {
        fetchData();
        return url;
    }
}
