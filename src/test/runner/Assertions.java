package test.runner;

public class Assertions
{
    /*
    void assertEquals(Object expected, Object actual)
    void assertFalse(boolean condition)
    void assertTrue(boolean condition)
    void assertNotNull(Object object)
    void assertNull(Object object)
    */

    public static void assertEquals(Object expected, Object actual)
    {
        if (!expected.equals(actual))
        {
            throw new AssertionError("Expected: " + expected + " but was: " + actual);
        }
    }

    public static void assertFalse(boolean condition)
    {
        if (condition)
        {
            throw new AssertionError("Assertion failed: expected condition to be false, but it was true.");
        }
    }

    public static void assertTrue(boolean condition)
    {
        if (!condition)
        {
            throw new AssertionError("Assertion failed: expected condition to be true, but it was false.");
        }
    }
    public static void assertNotNull(Object object)
    {
        if (object == null)
        {
            throw new AssertionError("Expected object to be not null, but it was null.");
        }
    }

    public static void assertNull(Object object)
    {
        if (object != null)
        {
            throw new AssertionError("Expected object to be null, but it was not.");
        }
    }
}
