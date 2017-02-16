package com.scorpiac.javarant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class Sort extends Option {
    public static final Sort ALGO = new Sort("algo", null);
    public static final Sort RECENT = new Sort("recent", null);

    private Sort(String name, NameValuePair option) {
        super(name, option);
    }

    public static Sort TOP(Range range) {
        return new Sort("top", new BasicNameValuePair("range", range.toString()));
    }
}
