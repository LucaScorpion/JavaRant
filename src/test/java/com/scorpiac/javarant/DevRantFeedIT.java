package com.scorpiac.javarant;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class DevRantFeedIT extends ITHelper {
    @Test
    public void testGetRants() throws IOException {
        server.stubFor(stubGet(
                get(urlPathEqualTo("/api/devrant/rants"))
                        .withQueryParam("limit", equalTo("4"))
                        .withQueryParam("skip", equalTo("1"))
                        .withQueryParam("sort", equalTo("recent")),
                "/feed-rants.json"
        ));

        Result<List<Rant>> result = devRant.getFeed().getRants(Sort.RECENT, 4, 1);
        assertFalse(result.getError().isPresent());
        List<Rant> rants = result.getValue().get();
        assertEquals(rants.size(), 4);

        validateRant(rants.get(0),
                814524,
                "Too real...",
                1,
                3,
                "tag one", "tag 2"
        );
    }

    @Test
    public void testSearch() throws IOException {
        server.stubFor(stubGet(
                get(urlPathEqualTo(ApiEndpoint.SEARCH.toString()))
                        .withQueryParam("term", equalTo("wtf")),
                "/search-wtf.json"
        ));

        Result<List<Rant>> result = devRant.getFeed().search("wtf");
        assertFalse(result.getError().isPresent());

        validateRant(result.getValue().get().get(1),
                542296,
                "Stages of learning angular js \n1. Wtf \n2. I think I get it. \n3. Wtf",
                327,
                19,
                "javascript", "angularjs", "wtf?"
        );
    }

    @Test
    public void testGetWeekly() throws IOException {
        server.stubFor(stubGet(
                get(urlPathEqualTo("/api/devrant/weekly-rants"))
                        .withQueryParam("skip", equalTo("2"))
                        .withQueryParam("sort", equalTo("algo")),
                "/feed-weekly.json"
        ));

        Result<List<Rant>> result = devRant.getFeed().getWeekly(Sort.ALGO, 2);
        assertFalse(result.getError().isPresent());
        List<Rant> rants = result.getValue().get();

        validateRant(rants.get(0),
                843118,
                "My own OCR library... so far I haven't found a proper recognizer",
                2,
                0,
                "wk69"
        );
    }

    @Test
    public void testGetStories() throws IOException {
        server.stubFor(stubGet(
                get(urlPathEqualTo("/api/devrant/story-rants"))
                        .withQueryParam("skip", equalTo("4"))
                        .withQueryParam("sort", equalTo("top")),
                "/feed-stories.json"
        ));

        Result<List<Rant>> result = devRant.getFeed().getStories(Sort.TOP, 4);
        assertFalse(result.getError().isPresent());
        List<Rant> rants = result.getValue().get();

        validateRant(rants.get(0),
                830647,
                "<Insert story rant here>",
                134,
                10,
                "mother", "web", "atom", "dreamweaver"
        );
    }

    @Test
    public void testGetCollabs() throws IOException {
        server.stubFor(stubGet(
                get(urlPathEqualTo("/api/devrant/collabs"))
                        .withQueryParam("limit", equalTo("2")),
                "/feed-collabs.json"
        ));

        Result<List<Rant>> result = devRant.getFeed().getCollabs(2);
        assertFalse(result.getError().isPresent());
        List<Rant> rants = result.getValue().get();

        validateRant(rants.get(0),
                838945,
                "Partnerships Matching App [more details]",
                12,
                6
        );
    }
}
