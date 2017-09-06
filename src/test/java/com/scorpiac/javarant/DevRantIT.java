package com.scorpiac.javarant;

import org.testng.annotations.Test;

import java.io.IOException;

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

        CommentedRant rant = devRant.getRant(686001).get();

        validateRant(
                rant,
                686001,
                "I only just noticed this is on the git man page :P",
                84,
                5,
                "terminal", "manual", "git"
        );

        assertEquals(rant.getCommentCount(), 5);
        assertEquals(rant.getComments().size(), 5);
        assertEquals(rant.getComments().get(0).getId(), 686175);

        validateImage(rant.getImage(), "https://img.devrant.io/devrant/rant/r_686001_VfN7X.jpg", 530, 134);
        validateMinimalUser(rant.getUser(), 102959, "LucaScorpion", 3831);
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

        validateUser(user,
                102959,
                "LucaScorpion",
                3831,
                "Software developer, fanatic programmer, hardcore gamer, Linux lover.",
                "Netherlands",
                "C#, Java, PHP, Javascript, HTML, CSS, SQL, C++ (Arduino), Bash",
                "LucaScorpion",
                "https://scorpiac.com",
                60,
                5103,
                800,
                36,
                0
        );
    }

    @Test
    public void testGetUserByUsernameInvalid() throws IOException {
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.USER_ID.toString()))
                        .withQueryParam("username", equalTo("invalid")),
                "/user-id-invalid.json"
        ));

        assertFalse(devRant.getUser("invalid").isPresent());
    }

    @Test
    public void testGetUserInvalid() throws IOException {
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.USERS.toString() + "/123")),
                "/user-id-invalid.json"
        ));

        assertFalse(devRant.getUser(123).isPresent());
    }

    @Test
    public void testGetSurprise() throws IOException {
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.SURPRISE.toString())),
                "/rant-surprise.json"
        ));

        Rant rant = devRant.getSurprise().get();

        validateRant(rant,
                26356,
                "Life of a software engineer.",
                91,
                1
        );

        validateMinimalUser(rant.getUser(),
                18401,
                "nhpace",
                218
        );

        validateImage(rant.getImage(),
                "https://img.devrant.io/devrant/rant/r_26356_N2S4f.jpg",
                504,
                381
        );
    }
}
