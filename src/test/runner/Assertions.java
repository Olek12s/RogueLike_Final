package test.runner;

import java.util.ArrayList;
import java.util.List;

public class Assertions
{
    private static int testCount = 0; // Total assertions count
    private static int passedCount = 0; // Passed assertions count
    private static List<String> errorMessages = new ArrayList<>(); // Collect error messages

    public static void resetCounts() {
        testCount = 0;
        passedCount = 0;
        errorMessages.clear();
    }

    public static int getTestCount() {
        return testCount;
    }

    public static int getPassedCount() {
        return passedCount;
    }

    public static List<String> getErrorMessages() {
        return errorMessages;
    }

    private static String formatMessage(String[] messages)
    {
        if (messages.length == 0) return "";
        else if (messages.length == 1) return messages[0] + ": ";
        else return String.join(", ", messages) + ": "; // concat elements of collection
    }

    public static void assertEquals(Object expected, Object actual, String... message)
    {
        testCount++;
        if (!expected.equals(actual))
        {
            errorMessages.add(formatMessage(message) + "Expected: " + expected + " but was: " + actual);
        } else {
            passedCount++;
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual, String... message)
    {
        testCount++;
        if (unexpected.equals(actual))
        {
            errorMessages.add(formatMessage(message) + "Expected not equal to: " + unexpected + " but was: " + actual);
        } else {
            passedCount++;
        }
    }

    public static void assertSame(Object expected, Object actual, String... message)
    {
        testCount++;
        if (expected != actual)
        {
            errorMessages.add(formatMessage(message) + "Expected same reference but found different: " + expected + " and " + actual);
        } else {
            passedCount++;
        }
    }

    public static void assertNotSame(Object unexpected, Object actual, String... message)
    {
        testCount++;
        if (unexpected == actual)
        {
            errorMessages.add(formatMessage(message) + "Expected different references, but both point to the same object: " + actual);
        } else {
            passedCount++;
        }
    }

    public static void assertFalse(boolean condition, String... message)
    {
        testCount++;
        System.out.println("test");
        if (condition)
        {
            errorMessages.add(formatMessage(message) + "Assertion failed: expected condition to be false, but it was true.");
        } else {
            passedCount++;
        }
    }

    public static void assertTrue(boolean condition, String... message)
    {
        testCount++;
        System.out.println("test");
        if (!condition)
        {
            errorMessages.add(formatMessage(message) + "Assertion failed: expected condition to be true, but it was false.");
        } else {
            passedCount++;
        }
    }

    public static void assertNotNull(Object object, String... message)
    {
        testCount++;
        if (object == null)
        {
            errorMessages.add(formatMessage(message) + "Expected object to be not null, but it was null.");
        } else {
            passedCount++;
        }
    }

    public static void assertNull(Object object, String... message)
    {
        testCount++;
        if (object != null)
        {
            errorMessages.add(formatMessage(message) + "Expected object to be null, but it was not.");
        } else {
            passedCount++;
        }
    }
}
