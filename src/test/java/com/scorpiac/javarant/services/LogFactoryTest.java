package com.scorpiac.javarant.services;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LogFactoryTest {
    @Test
    public void testLoggerName() {
        assertEquals(
                LogFactory.getLog().getName(),
                getClass().getName()
        );
    }
}
