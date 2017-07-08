package com.scorpiac.javarant;

import java.util.Collections;

public class Collab extends Rant {
    private String projectType;

    // Data that needs to be fetched.
    private boolean fetched = false;
    private String description;
    private String techStack;
    private String teamSize;
    private String url;

    protected Collab(DevRant devRant, int id, User user, int upvotes, int downvotes, int score, int voteState, String projectType, String summary, int commentCount) {
        super(devRant, id, user, upvotes, downvotes, score, voteState, summary, null, Collections.emptyList(), commentCount);
        this.projectType = projectType;
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
