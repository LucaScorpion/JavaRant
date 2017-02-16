package com.scorpiac.javarant;

import org.apache.http.NameValuePair;

class Option {
    private final String name;
    private final NameValuePair parameter;

    protected Option(String name, NameValuePair parameter) {
        this.name = name;
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the parameter for this {@link Option}, or {@code null} if it has no option.
     */
    public NameValuePair getParameter() {
        return parameter;
    }
}
