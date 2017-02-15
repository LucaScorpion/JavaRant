package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Rant extends RantContent {
    private String[] tags;
    private int commentCount;
    private Comment[] comments;

    protected Rant(DevRant devRant, int id, User user, int upvotes, int downvotes, int score, String text, Image image, String[] tags, int commentCount) {
        super(devRant, id, user, upvotes, downvotes, score, text, image);
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
                json.get("text").getAsString(),
                Image.fromJson(json.get("attached_image")),
                Util.jsonToList(json.getAsJsonArray("tags"), JsonElement::getAsString).toArray(new String[0]),
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
        comments = Util.jsonToList(commentArray, elem -> Comment.fromJson(devRant, elem.getAsJsonObject())).toArray(new Comment[0]);
    }

    /**
     * Get the comments on this rant. If they are not yet retrieved, this will also fetch them.
     *
     * @return The comments.
     */
    public Comment[] getComments() {
        fetchComments();
        return comments;
    }

    /**
     * Fetch and store the comments on this rant. If the comments are already fetched, they will not be fetched again.
     *
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchComments() {
        return fetchComments(false);
    }

    /**
     * Fetch and store the comments on this rant.
     *
     * @param force Whether to fetch the data even if it was already fetched.
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchComments(boolean force) {
        // Check if we already fetched and force is false.
        if (comments != null && !force)
            return true;

        // Rants url, rant id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_RANTS, getId(), DevRant.APP_ID);
        JsonObject json = devRant.get(url);

        // Check for success.
        if (!Util.jsonSuccess(json))
            return false;

        // Get the comments.
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
