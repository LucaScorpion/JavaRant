package com.scorpiac.javarant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Vote {
    /**
     * An upvote.
     */
    public static final Vote UP = new Vote("1");
    /**
     * A neutral vote.
     */
    public static final Vote NONE = new Vote("0");

    private final String value;

    private Vote(String value) {
        this.value = value;
    }

    /**
     * A downvote.
     *
     * @param reason The reason for the downvote.
     * @return A downvote.
     */
    public static Vote DOWN(Reason reason) {
        return new DownVote(reason);
    }

    List<NameValuePair> getOptions() {
        List<NameValuePair> options = new ArrayList<>();
        options.add(new BasicNameValuePair("vote", value));
        return options;
    }

    private static class DownVote extends Vote {
        private final Reason reason;

        private DownVote(Reason reason) {
            super("-1");
            this.reason = reason;
        }

        @Override
        List<NameValuePair> getOptions() {
            List<NameValuePair> options = super.getOptions();
            options.add(new BasicNameValuePair("reason", reason.toString()));
            return options;
        }
    }
}
