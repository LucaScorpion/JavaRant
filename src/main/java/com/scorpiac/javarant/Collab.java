package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scorpiac.javarant.exceptions.NotACollabException;

import java.util.Collections;

public class Collab extends Rant {
    private String projectType;

    // Data that needs to be fetched.
    private boolean fetched = false;
    private String description;
    private String techStack;
    private String teamSize;
    private String url;

    protected Collab(DevRant devRant, int id, User user, int upvotes, int downvotes, int score, String projectType, String summary, int commentCount) {
        super(devRant, id, user, upvotes, downvotes, score, summary, null, Collections.emptyList(), commentCount);
        this.projectType = projectType;
    }

    static Collab fromJson(DevRant devRant, JsonObject json) {
        // Check if the rant is also a collab.
        if (!json.has("c_type_long"))
            throw new NotACollabException(json.get("id").getAsInt());

        return new Collab(
                devRant,
                json.get("id").getAsInt(),
                User.fromJson(devRant, json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("score").getAsInt(),
                json.get("c_type_long").getAsString(),
                json.get("text").getAsString(),
                json.get("num_comments").getAsInt()
        );
    }

    static Collab fromJson(DevRant devRant, JsonObject collab, JsonArray comments) {
        Collab result = fromJson(devRant, collab);
        result.setData(collab, comments);
        result.fetched = true;
        return result;
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

        JsonObject json = devRant.get(DevRant.API_RANTS + '/' + getId());

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
        return DevRant.BASE_URL + DevRant.COLLAB_URL + '/' + getId();
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
