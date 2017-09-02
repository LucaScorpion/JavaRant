package com.scorpiac.javarant;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class DevRantTest {
    @Test
    public void testInit() {
        DevRant devRant = new DevRant();

        assertNotNull(devRant.getFeed());
        assertFalse(devRant.isLoggedIn());
        devRant.logout();
    }
}
