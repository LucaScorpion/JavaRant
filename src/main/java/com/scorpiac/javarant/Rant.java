package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;

public class Rant extends RantContent {
    private List<String> tags;
    private int commentCount;
    private List<Comment> comments;

    protected Rant(DevRant devRant, int id, User user, int upvotes, int downvotes, int score, int voteState, String text, Image image, List<String> tags, int commentCount) {
        super(devRant, id, user, upvotes, downvotes, score, voteState, text, image);
        this.tags = tags;
        this.commentCount = commentCount;
    }

    static Rant fromJson(DevRant devRant, JsonObject json) {
        return new Rant(
                devRant,
                json.get("id").getAsInt(),
                User.fromJson(devRant, json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("score").getAsInt(),
                json.get("vote_state").getAsInt(),
                json.get("text").getAsString(),
                Image.fromJson(json.get("attached_image")),
                Util.jsonToList(json.getAsJsonArray("tags"), JsonElement::getAsString),
                json.get("num_comments").getAsInt()
        );
    }

    static Rant fromJson(DevRant devRant, JsonObject rant, JsonArray comments) {
        Rant result = fromJson(devRant, rant);
        result.commentsFromJson(comments);
        return result;
    }

    /**
     * Set the comments from a JSON array.
     *
     * @param commentArray The JSON array to get the comments from.
     */
    protected void commentsFromJson(JsonArray commentArray) {
        comments = Util.jsonToList(commentArray, elem -> Comment.fromJson(devRant, elem.getAsJsonObject()));
    }

    /**
     * Get the comments on this rant. If they are not yet retrieved, this will also fetch them.
     *
     * @return The comments.
     */
    public List<Comment> getComments() {
        fetchComments();
        return Collections.unmodifiableList(comments);
    }

    /**
     * Fetch the comments on this rant. If the comments are already fetched, they will not be fetched again.
     *
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchComments() {
        return fetchComments(false);
    }

    /**
     * Fetch the comments on this rant.
     *
     * @param force Whether to fetch the comments even if it they are already fetched.
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchComments(boolean force) {
        // Check if we already fetched and force is false.
        if (comments != null && !force)
            return true;
        
        JsonObject json = devRant.get(DevRant.API_RANTS + '/' + getId());

        if (!Util.jsonSuccess(json))
            return false;

        commentsFromJson(json.getAsJsonArray("comments"));
        return true;
    }

    /**
     * Get the link to the rant.
     */
    public String link() {
        return DevRant.BASE_URL + DevRant.RANT_URL + '/' + getId();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Rant;
    }

    /**
     * Get the tags.
     */
    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    /**
     * Get the amount of comments.
     */
    public int getCommentCount() {
        return commentCount;
    }
}
