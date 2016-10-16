package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.scorpiac.javarant.exceptions.NoSuchRantException;

public class Rant extends RantContent {
    private String image;
    private String[] tags;
    private int commentCount;
    private Comment[] comments;

    private Rant(int id, User user, int upvotes, int downvotes, String text, String image, String[] tags, int commentCount) {
        super(id, user, upvotes, downvotes, text);
        this.image = image;
        this.tags = tags;
        this.commentCount = commentCount;
    }

    /**
     * Get a rant by its id. This will also retrieve the comments.
     *
     * @param id The id of the rant to get.
     * @return The rant.
     */
    public static Rant byId(int id) {
        // Users url, rant id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_RANTS_URL, id, DevRant.APP_ID);
        JsonObject json = DevRant.request(url);

        // Check if the rant exists.
        if (!Util.jsonSuccess(json))
            throw new NoSuchRantException(id);

        // Get the rant and comments.
        Rant rant = fromJson(json.get("rant").getAsJsonObject());
        rant.commentsFromJson(json.get("comments").getAsJsonArray());

        return rant;
    }

    static Rant fromJson(JsonObject json) {
        return new Rant(
                json.get("id").getAsInt(),
                User.fromJson(json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("text").getAsString(),
                getImage(json.get("attached_image")),
                Util.jsonToList(json.getAsJsonArray("tags"), JsonElement::getAsString).toArray(new String[0]),
                json.get("num_comments").getAsInt()
        );
    }

    /**
     * Set the comments from a JSON array.
     *
     * @param commentArray The JSON array to get the comments from.
     */
    private void commentsFromJson(JsonArray commentArray) {
        comments = Util.jsonToList(commentArray, elem -> Comment.fromJson(elem.getAsJsonObject())).toArray(new Comment[0]);
    }

    private static String getImage(JsonElement image) {
        return image.isJsonObject() ? image.getAsJsonObject().get("url").getAsString() : "";
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
     *
     * @return Whether the data was fetched successfully.
     */
    public boolean fetchComments() {
        // Rants url, rant id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_RANTS_URL, this.getId(), DevRant.APP_ID);
        JsonObject json = DevRant.request(url);

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
    public String rantLink() {
        return DevRant.link(DevRant.RANT_URL + "/" + this.getId());
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
