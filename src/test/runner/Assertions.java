package test.runner;

import java.util.Arrays;

public class Assertions
{
    private static int testCount = 0;

    public static void resetTestCount() {testCount = 0;}
    public static int getTestCount() {return testCount;}
    /*
    void assertEquals(Object expected, Object actual)
    void assertNotEquals(Object unexpected, Object actual)
    void assertSame(Object expected, Object actual)
    void assertNotSame(Object unexpected, Object actual)
    void assertFalse(boolean condition)
    void assertTrue(boolean condition)
    void assertNotNull(Object object)
    void assertNull(Object object)
    */

    private static String formatMessage(String[] messages)
    {
        if (messages.length == 0) return  "";
        else if (messages.length == 1) return messages[0] + ": ";
        else return String.join(", ", messages) + ": "; // concat elements of collection
    }

    public static void assertEquals(Object expected, Object actual, String ... message)
    {
        testCount++;
        if (!expected.equals(actual))
        {
            throw new AssertionError(formatMessage(message) + "Expected: " + expected + " but was: " + actual);
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual, String ... message)
    {
        testCount++;
        if (unexpected.equals(actual))
        {
            throw new AssertionError(formatMessage(message) + "Expected not equal to: " + unexpected + " but was: " + actual);
        }
    }

    public static void assertSame(Object expected, Object actual, String ... message)
    {
        testCount++;
        if (expected != actual)
        {
            throw new AssertionError(formatMessage(message) + "Expected same reference but found different: " + expected + " and " + actual);
        }
    }

    public static void assertNotSame(Object unexpected, Object actual, String ... message)
    {
        testCount++;
        if (unexpected == actual)
        {
            throw new AssertionError(formatMessage(message) + "Expected different references, but both point to the same object: " + actual);
        }
    }

    public static void assertFalse(boolean condition, String ... message)
    {
        testCount++;
        if (condition)
        {
            throw new AssertionError(formatMessage(message) + "Assertion failed: expected condition to be false, but it was true.");
        }
    }

    public static void assertTrue(boolean condition, String ... message)
    {
        testCount++;
        if (!condition)
        {
            throw new AssertionError(formatMessage(message) + "Assertion failed: expected condition to be true, but it was false.");
        }
    }
    public static void assertNotNull(Object object, String ... message)
    {
        testCount++;
        if (object == null)
        {
            throw new AssertionError(formatMessage(message) + "Expected object to be not null, but it was null.");
        }
    }

    public static void assertNull(Object object, String ... message)
    {
        testCount++;
        if (object != null)
        {
            throw new AssertionError(formatMessage(message) + "Expected object to be null, but it was not.");
        }
    }
}
