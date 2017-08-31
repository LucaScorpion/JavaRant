package com.scorpiac.javarant;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class DevRantTest {
    private DevRant devRant;

    @BeforeClass
    public void beforeClass() {
        // TODO: use Wiremock.
        devRant = new DevRant();
    }

    @Test
    public void testGetRant() {
        Rant rant = devRant.getRant(686001).get();

        assertEquals(rant.getId(), 686001);
        assertEquals(rant.getText(), "I only just noticed this is on the git man page :P");
        assertEquals(rant.getScore(), 84);
        assertEquals(rant.getTags(), Arrays.asList("terminal", "manual", "git"));
        assertEquals(rant.getVoteState(), VoteState.NONE);

        // Comments.
        assertEquals(rant.getCommentCount(), 5);
        assertEquals(rant.getComments().size(), 5);
        assertEquals(rant.getComments().get(0).getId(), 686175);

        // Image.
        assertEquals(rant.getImage().getWidth(), 530);
        assertEquals(rant.getImage().getHeight(), 134);
        assertEquals(rant.getImage().getLink(), "https://img.devrant.io/devrant/rant/r_686001_VfN7X.jpg");

        // User.
        assertEquals(rant.getUser().getId(), 102959);
        assertEquals(rant.getUser().getUsername(), "LucaScorpion");
        assertEquals(rant.getUser().getScore(), 3831);
    }
}
