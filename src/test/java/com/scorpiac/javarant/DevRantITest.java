package com.scorpiac.javarant;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.scorpiac.javarant.services.MockRequestHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.testng.Assert.assertEquals;

public class DevRantITest {
    private DevRant devRant;

    private WireMockServer server;

    @BeforeClass
    public void beforeClass() {
        server = new WireMockServer(
                options()
                        .dynamicPort()
        );
        server.start();

        devRant = new DevRant();
        devRant.setRequestHandler(new MockRequestHandler(server.port()));
    }

    @AfterClass
    public void afterClass() {
        server.stop();
    }

    @AfterMethod
    public void resetServer() {
        server.resetAll();
    }

    private MappingBuilder stubResponse(MappingBuilder stub, String resource) throws IOException {
        String responseString;
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(resource))) {
            responseString = scanner.useDelimiter("\\A").next();
        }

        return stub
                .withQueryParam("app", equalTo("3"))
                .withQueryParam("plat", equalTo("3"))
                .willReturn(aResponse().withBody(responseString));
    }

    @Test
    public void testGetRant() throws IOException {
        server.stubFor(stubResponse(
                get(Endpoint.RANTS.toString() + '/' + 686001 + "?app=3&plat=3"),
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
}
