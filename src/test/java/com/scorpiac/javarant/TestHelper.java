package com.scorpiac.javarant;

import java.net.URI;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public abstract class TestHelper {
    public void validateRantContent(RantContent rant, int id, String text, int score) {
        assertEquals(rant.getId(), id);
        assertEquals(rant.getText(), text);
        assertEquals(rant.getScore(), score);
    }

    public void validateRant(Rant rant, int id, String text, int score, int commentCount, String... tags) {
        validateRantContent(rant, id, text, score);

        assertEquals(rant.getCommentCount(), commentCount);
        assertEquals(rant.getLink(), URI.create("https://www.devrant.io/rants/" + id));
        assertEquals(rant.getTags(), Arrays.asList(tags));
    }

    public void validateCollab(Collab collab, int id, String text, int score, int commentCount, String projectType, String description, String techStack, String teamSize, String url, String... tags) {
        validateRant(collab, id, text, score, commentCount);

        assertEquals(collab.getProjectType(), projectType);
        assertEquals(collab.getTechStack(), techStack);
        assertEquals(collab.getDescription(), description);
        assertEquals(collab.getTeamSize(), teamSize);
        assertEquals(collab.getUrl(), url);
    }

    public void validateImage(Image image, String link, int width, int height) {
        assertEquals(image.getLink(), URI.create(link));
        assertEquals(image.getWidth(), width);
        assertEquals(image.getHeight(), height);
    }

    public void validateMinimalUser(MinimalUser user, int id, String username, int score) {
        assertEquals(user.getId(), id);
        assertEquals(user.getUsername(), username);
        assertEquals(user.getScore(), score);
    }

    public void validateUser(User user, int id, String username, int score, String about, String location, String skills, String github, String website,
                             int rants, int upvoted, int comments, int favorites, int collabs) {
        validateMinimalUser(user, id, username, score);

        assertEquals(user.getAbout(), about);
        assertEquals(user.getLocation(), location);
        assertEquals(user.getSkills(), skills);
        assertEquals(user.getGithub(), github);
        assertEquals(user.getWebsite(), website);

        assertEquals(user.getRantsCount(), rants);
        assertEquals(user.getUpvotedCount(), upvoted);
        assertEquals(user.getCommentsCount(), comments);
        assertEquals(user.getFavoritesCount(), favorites);
        assertEquals(user.getCollabsCount(), collabs);
    }
}
