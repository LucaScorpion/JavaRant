package com.scorpiac.javarant;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

public class DevRantSmokeTestIT {
    private final DevRant devRant = new DevRant();

    @BeforeClass
    public void checkShouldTest() {
        if (System.getProperty("javarant.test.smoke") == null) {
            throw new SkipException("Property 'javarant.test.smoke' is not set, skipping smoke tests.");
        }
    }

    @Test
    public void testGetRant() {
        Rant rant = devRant.getRant(892667);

        assertEquals(rant.getId(), 892667);
        assertEquals(rant.getUser().getUsername(), "LucaScorpion");
        assertNotNull(rant.getImage());
    }

    @Test(expectedExceptions = NoSuchRantException.class, expectedExceptionsMessageRegExp = ".*123.*")
    public void testGetRantInvalid() {
        devRant.getRant(123);
    }

    @Test
    public void testGetUserByUsername() {
        User user = devRant.getUser("LucaScorpion");

        assertEquals(user.getUsername(), "LucaScorpion");
        assertEquals(user.getId(), 102959);
        assertFalse(user.getRants().isEmpty());
        assertTrue(user.getRantsCount() > 0);
    }

    @Test(expectedExceptions = NoSuchUsernameException.class, expectedExceptionsMessageRegExp = ".*'This-is-a-non-existing-username.*'.*")
    public void testGetUserByUsernameInvalid() {
        // Add some randomness in case someone wants to fuck this test by registering this username.
        devRant.getUser("This-is-a-non-existing-username-" + UUID.randomUUID().toString());
    }

    @Test(expectedExceptions = NoSuchUserIdException.class, expectedExceptionsMessageRegExp = ".*123.*")
    public void testGetUserInvalid() {
        devRant.getUser(123);
    }
}
