package com.scorpiac.javarant;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class DevRantIT extends ITHelper {
    @Test
    public void testGetRant() throws IOException {
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.RANTS.toString() + "/686001")),
                "/rant-686001.json"
        ));

        Rant rant = devRant.getRant(686001).get();

        assertEquals(rant.getId(), 686001);
        assertEquals(rant.getText(), "I only just noticed this is on the git man page :P");
        assertEquals(rant.getScore(), 84);
        assertEquals(rant.getTags(), Arrays.asList("terminal", "manual", "git"));
        assertEquals(rant.getVoteState(), VoteState.NONE);

        // Comments.
        assertEquals(rant.getCommentCount(), 5);
        assertEquals(rant.getComments().size(), 5);
        assertEquals(rant.getComments().get(0).getId(), 686175);

        // Image.
        assertEquals(rant.getImage().getWidth(), 530);
        assertEquals(rant.getImage().getHeight(), 134);
        assertEquals(rant.getImage().getLink(), "https://img.devrant.io/devrant/rant/r_686001_VfN7X.jpg");

        // User.
        assertEquals(rant.getUser().getId(), 102959);
        assertEquals(rant.getUser().getUsername(), "LucaScorpion");
        assertEquals(rant.getUser().getScore(), 3831);
    }

    @Test
    public void testGetInvalidRant() throws IOException {
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.RANTS.toString() + "/0")),
                "/rant-invalid.json"
        ));

        assertFalse(devRant.getRant(0).isPresent());
    }

    @Test
    public void testGetUserByUsername() throws IOException {
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.USER_ID.toString()))
                        .withQueryParam("username", equalTo("LucaScorpion")),
                "/user-id-LucaScorpion.json"
        ));
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.USERS.toString() + "/102959")),
                "/user-102959.json"
        ));

        User user = devRant.getUser("LucaScorpion").get();

        assertEquals(user.getId(), 102959);
        assertEquals(user.getUsername(), "LucaScorpion");
        assertEquals(user.getScore(), 3831);
        assertEquals(user.getAbout(), "Software developer, fanatic programmer, hardcore gamer, Linux lover.");
        assertEquals(user.getLocation(), "Netherlands");
        assertEquals(user.getSkills(), "C#, Java, PHP, Javascript, HTML, CSS, SQL, C++ (Arduino), Bash");
        assertEquals(user.getGithub(), "LucaScorpion");
        assertEquals(user.getWebsite(), "https://scorpiac.com");

        // Counts.
        assertEquals(user.getRantsCount(), 60);
        assertEquals(user.getUpvotedCount(), 5103);
        assertEquals(user.getCommentsCount(), 800);
        assertEquals(user.getFavoritesCount(), 36);
        assertEquals(user.getCollabsCount(), 0);
    }
}
