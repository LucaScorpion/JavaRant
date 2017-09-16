package com.scorpiac.javarant;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class DevRantAuthIT extends ITHelper {
    private String authBody;

    @BeforeClass
    public void login() {
        devRant.auth = new Auth("123", "t0k3n", "456");
        authBody = "token_id=123&token_key=t0k3n&user_id=456";
    }

    @Test
    public void testUpvoteRant() throws IOException {
        server.stubFor(stubPost(
                post(urlPathEqualTo(ApiEndpoint.RANTS.toString() + "/843654/" + ApiEndpoint.VOTE.toString()))
                        .withRequestBody(equalTo("vote=1&" + authBody + "&app=3&plat=3")),
                "/vote-rant-up-843654.json"
        ));

        Result<Rant> result = devRant.getAuth().voteRant(843654, Vote.UP);
        assertFalse(result.getError().isPresent());
        assertEquals(result.getValue().get().getVoteState(), VoteState.UP);
    }

    @Test
    public void testDownvoteRant() throws IOException {
        server.stubFor(stubPost(
                post(urlPathEqualTo(ApiEndpoint.RANTS.toString() + "/843654/" + ApiEndpoint.VOTE.toString()))
                        .withRequestBody(equalTo("vote=-1&reason=0&" + authBody + "&app=3&plat=3")),
                "/vote-rant-down-843654.json"
        ));

        Result<Rant> result = devRant.getAuth().voteRant(843654, Vote.DOWN(Reason.NOT_FOR_ME));
        assertFalse(result.getError().isPresent());
        assertEquals(result.getValue().get().getVoteState(), VoteState.DOWN);
    }
}
