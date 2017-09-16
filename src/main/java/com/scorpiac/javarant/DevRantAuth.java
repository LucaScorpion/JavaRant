package com.scorpiac.javarant;

import com.scorpiac.javarant.responses.CommentResponse;
import com.scorpiac.javarant.responses.RantResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class DevRantAuth {
    private final DevRant devRant;

    DevRantAuth(DevRant devRant) {
        this.devRant = devRant;
    }

    public Result<Rant> voteRant(int id, Vote vote) {
        return devRant.getRequestHandler().post(
                ApiEndpoint.RANTS.toString() + '/' + id + '/' + ApiEndpoint.VOTE.toString(),
                RantResponse.class,
                getParameters(vote.getOptions())
        );
    }

    public Result<Comment> voteComment(int id, Vote vote) {
        return devRant.getRequestHandler().post(
                ApiEndpoint.COMMENTS.toString() + '/' + id + '/' + ApiEndpoint.VOTE.toString(),
                CommentResponse.class,
                getParameters(vote.getOptions())
        );
    }

    private NameValuePair[] getParameters(List<NameValuePair> params) {
        // Add the auth parameters.
        params.add(new BasicNameValuePair("token_id", devRant.auth.getId()));
        params.add(new BasicNameValuePair("token_key", devRant.auth.getKey()));
        params.add(new BasicNameValuePair("user_id", devRant.auth.getUserId()));

        return params.toArray(new NameValuePair[0]);
    }
}
