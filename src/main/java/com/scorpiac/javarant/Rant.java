package com.scorpiac.javarant;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Rant extends RantContent {
    private String image;
    private String[] tags;
    private int commentCount;
    private Comment[] comments;

    public Rant(int id, User user, int upvotes, int downvotes, String text, String image, String[] tags, int commentCount) {
        super(id, user, upvotes, downvotes, text);
        this.image = image;
        this.tags = tags;
        this.commentCount = commentCount;
    }

    static Rant fromJson(JsonObject json) {
        return new Rant(
                json.get("id").getAsInt(),
                User.fromJson(json),
                json.get("num_upvotes").getAsInt(),
                json.get("num_downvotes").getAsInt(),
                json.get("text").getAsString(),
                getImage(json.get("attached_image")),
                Util.jsonToList(json.get("tags").getAsJsonArray(), JsonElement::getAsString).toArray(new String[0]),
                json.get("num_comments").getAsInt()
        );
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
     */
    public void fetchComments() {
        // Rants url, rant id, app id.
        String url = String.format("%1$s/%2$d?app=%3$s", DevRant.API_RANTS_URL, this.getId(), DevRant.APP_ID);
        JsonArray commentsJson = DevRant.request(url).get("comments").getAsJsonArray();

        comments = Util.jsonToList(commentsJson, elem -> Comment.fromJson(elem.getAsJsonObject())).toArray(new Comment[0]);
    }

    /**
     * Get the link to the rant.
     */
    public String rantLink() {
        return DevRant.RANT_URL + "/" + this.getId();
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
