package com.scorpiac.javarant;

public class Comment extends RantContent {

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Comment;
    }
}
