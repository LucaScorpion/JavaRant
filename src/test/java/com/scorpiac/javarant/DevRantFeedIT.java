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
                get(urlPathEqualTo(ApiEndpoint.RANTS.toString()))
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
}
