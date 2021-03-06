package com.scorpiac.javarant;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testng.Assert.assertEquals;

public class DevRantAuthIT extends ITHelper {
    private String authBody;

    @BeforeClass
    public void login() {
        devRant.setAuthObject(new Auth("123", "t0k3n", "456"));
        authBody = "token_id=123&token_key=t0k3n&user_id=456";
    }

    @Test
    public void testUpvoteRant() throws IOException {
        server.stubFor(stubPost(
                post(urlPathEqualTo("/api/devrant/rants/843654/vote"))
                        .withRequestBody(equalTo("vote=1&" + authBody + "&app=3&plat=3")),
                "/vote-rant-up-843654.json"
        ));

        Rant rant = devRant.getAuth().voteRant(843654, Vote.UP);
        assertEquals(rant.getVoteState(), VoteState.UP);
    }

    @Test
    public void testDownvoteRant() throws IOException {
        server.stubFor(stubPost(
                post(urlPathEqualTo("/api/devrant/rants/843654/vote"))
                        .withRequestBody(equalTo("vote=-1&reason=0&" + authBody + "&app=3&plat=3")),
                "/vote-rant-down-843654.json"
        ));

        Rant rant = devRant.getAuth().voteRant(843654, Vote.DOWN(Reason.NOT_FOR_ME));
        assertEquals(rant.getVoteState(), VoteState.DOWN);
    }

    @Test
    public void testNonevoteComment() throws IOException {
        server.stubFor(stubPost(
                post(urlPathEqualTo("/api/comments/843736/vote"))
                        .withRequestBody(equalTo("vote=0&" + authBody + "&app=3&plat=3")),
                "/vote-comment-none-843736.json"
        ));

        Comment comment = devRant.getAuth().voteComment(843736, Vote.NONE);
        assertEquals(comment.getVoteState(), VoteState.NONE);
    }
}
