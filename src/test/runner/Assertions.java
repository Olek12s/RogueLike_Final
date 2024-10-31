package test.runner;

import java.util.ArrayList;
import java.util.Arrays;

public class Assertions
{
    private static int testCount = 0;
    private static int passedCount = 0;
    private static int totalAssertions = 0;
    private static int totalAssertionsPassed = 0;
    private static ArrayList<String> errorMessages = new ArrayList<>();


    public static void resetCounts()
    {
        testCount = 0;
        passedCount = 0;
        errorMessages.clear();
    }

    public static int getTestCount() {return testCount;}
    public static int getPassedCount() {return passedCount;}
    public static int getTotalAssertions() {return totalAssertions;}
    public static int getTotalAssertionsPassed() {return totalAssertionsPassed;}

    public static ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    private static String formatMessage(String[] messages)
    {
        if (messages.length == 0) return  "";
        else if (messages.length == 1) return messages[0] + ": ";
        else return String.join(", ", messages) + ": "; // concat elements of collection
    }

    public static void assertEquals(Object expected, Object actual, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (!expected.equals(actual))
        {
            errorMessages.add(formatMessage(message) + "Expected: " + expected + " but was: " + actual);
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (unexpected.equals(actual))
        {
            errorMessages.add(formatMessage(message) + "Expected not equal to: " + unexpected + " but was: " + actual);
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }

    public static void assertSame(Object expected, Object actual, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (expected != actual)
        {
            errorMessages.add(formatMessage(message) + "Expected same reference but found different: " + expected + " and " + actual);
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }

    public static void assertNotSame(Object unexpected, Object actual, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (unexpected == actual)
        {
            errorMessages.add(formatMessage(message) + "Expected different references, but both point to the same object: " + actual);
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }

    public static void assertFalse(boolean condition, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (condition)
        {
            errorMessages.add(formatMessage(message) + "Assertion failed: expected condition to be false, but it was true.");
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }

    public static void assertTrue(boolean condition, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (!condition)
        {
            errorMessages.add(formatMessage(message) + "Assertion failed: expected condition to be true, but it was false.");
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }
    public static void assertNotNull(Object object, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (object == null)
        {
            errorMessages.add(formatMessage(message) + "Expected object to be not null, but it was null.");
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }

    public static void assertNull(Object object, String ... message)
    {
        totalAssertions++;
        testCount++;
        if (object != null)
        {
            errorMessages.add(formatMessage(message) + "Expected object to be null, but it was not.");
        }
        else
        {
            totalAssertionsPassed++;
            passedCount++;
        }
    }
}
