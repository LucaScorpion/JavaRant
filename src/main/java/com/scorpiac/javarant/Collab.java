package com.scorpiac.javarant;

import com.scorpiac.javarant.services.RequestHandler;

import java.net.URI;

public class Collab extends Rant {
    private String projectType;

    // Data that needs to be fetched.
    private boolean fetched = false;
    private String description;
    private String techStack;
    private String teamSize;
    private String url;

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
    public URI link() {
        return RequestHandler.BASE_URI.resolve(DevRant.COLLAB_URL).resolve(String.valueOf(getId()));
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
