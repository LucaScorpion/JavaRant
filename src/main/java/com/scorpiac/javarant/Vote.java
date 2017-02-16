package com.scorpiac.javarant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class Vote extends Option {
    public static final Vote UP = new Vote("1", null);
    public static final Vote NONE = new Vote("0", null);

    private Vote(String value, NameValuePair parameter) {
        super(value, parameter);
    }

    public static Vote DOWN(Reason reason) {
        return new Vote("-1", new BasicNameValuePair("reason", reason.toString()));
    }
}
