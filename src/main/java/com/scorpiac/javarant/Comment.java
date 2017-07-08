package com.scorpiac.javarant;

public class Comment extends RantContent {
    protected Comment(int id, User user, int upvotes, int downvotes, int score, int voteState, String content, Image image) {
        super(id, user, upvotes, downvotes, score, voteState, content, image);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Comment;
    }
}
