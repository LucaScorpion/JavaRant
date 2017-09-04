package com.scorpiac.javarant;

import com.scorpiac.javarant.services.MockRequestHandler;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testng.Assert.assertEquals;

public class DevRantFeedIT extends ITHelper {
    @Test
    public void testGetRants() throws IOException {
        devRant.setRequestHandler(new MockRequestHandler(server.port()));
        
        server.stubFor(stubResponse(
                get(urlPathEqualTo(Endpoint.RANTS.toString()))
                        .withQueryParam("limit", equalTo("4"))
                        .withQueryParam("skip", equalTo("1"))
                        .withQueryParam("sort", equalTo("recent")),
                "/feed-rants.json"
        ));

        List<MinimalRant> rants = devRant.getFeed().getRants(Sort.RECENT, 4, 1).get();
        assertEquals(rants.size(), 4);

        validateRant(rants.get(0),
                814524,
                "Too real...",
                1,
                3,
                "tag one", "tag 2"
        );
    }
}
