package basic_demo;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AppTest {
    @Test
    public void testAppHasAGreeting() {
        basic_demo.App classUnderTest = new basic_demo.App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}