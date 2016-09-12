package com.scorpiac.javarant;

import java.util.ArrayList;
import java.util.List;

public class Rant extends RantContent {
    private int id;
    private String image;
    private String[] tags;
    private int commentCount;
    private Comment[] comments;

    public Rant(int id, String author, int score, String text, String image, String[] tags, int commentCount) {
        super(author, score, text);
        this.id = id;
        this.image = image;
        this.tags = tags;
        this.commentCount = commentCount;
    }

    /**
     * Get the comments on this rant. If they are not yet retrieved, this will also fetch them.
     *
     * @return The comments.
     */
    public Comment[] getComments() {
        if (comments == null)
            fetchComments();
        return comments;
    }

    /**
     * Fetch and store the comments on this rant.
     */
    public void fetchComments() {
        List<Comment> comments = new ArrayList<>(commentCount);

        // Save the comments and comment count.
        this.comments = comments.toArray(new Comment[comments.size()]);
        commentCount = this.comments.length;
    }

    /**
     * Get the rant id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the link to the rant.
     */
    public String rantLink() {
        return DevRant.RANT_URL + id;
    }

    /**
     * Get the link to the image, or null if there is none.
     */
    public String imageLink() {
        return image;
    }

    /**
     * Get the tags from this rant.
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Get the amount of comments on this rant.
     */
    public int getCommentCount() {
        return commentCount;
    }
}
