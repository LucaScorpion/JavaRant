package com.scorpiac.javarant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Collab extends CommentedRant {
    @JsonProperty("c_type_long")
    private String projectType;
    @JsonProperty("c_tech_stack")
    private String techStack;
    @JsonProperty("c_team_size")
    private String teamSize;
    @JsonProperty("c_url")
    private String url;
    @JsonProperty("c_description")
    private String description;

    /**
     * Get the project type.
     *
     * @return The project type.
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * Get the project description.
     *
     * @return The project description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the project tech stack.
     *
     * @return The project tech stack.
     */
    public String getTechStack() {
        return techStack;
    }

    /**
     * Get the team size.
     *
     * @return The team size.
     */
    public String getTeamSize() {
        return teamSize;
    }

    /**
     * Get the project url.
     *
     * @return The project url.
     */
    public String getUrl() {
        return url;
    }
}
