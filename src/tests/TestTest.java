package tests;

import test.annotations.*;
import test.runner.*;

public class TestTest
{
    @Before
    public void setUp()
    {

    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testAdd()
    {
        Assertions.assertEquals(5, 2+3, "[Adding 2 digits]");
    }

    @Test
    public void testSubtract()
    {
        Assertions.assertEquals(2, 4-2, "[Subtracting 2 digits]");
    }

    @Test
    public void testAssertFalse()
    {
        Assertions.assertFalse((1+1) == 3, "[Comparing, if 1+1 = 2 isn't two");
    }

    @Test
    public void testAssertTrue()
    {
        Assertions.assertTrue((3 == 3), "[Comparing, if 1+1 = 3 is true");
    }

    @Test
    public void testAssertNotNull()
    {
        Assertions.assertNotNull(new Object(), "[Checking if myMath object exists]");
    }

    @Test
    public void testAssertNull()
    {
        tearDown();
        Assertions.assertNull(null, "[Checking if myMath object does not exist]");
    }

    @Test
    public void testAssertNotEquals()
    {
        Assertions.assertNotEquals(3, 4, "[Checking, if 2+2 equals 3]");
    }

    @Test
    public void testAssertSame()
    {
        Object instance = new Object();
        Assertions.assertSame(instance, instance, "[Checking if two instances of the same class are same]");
    }

    @Test
    public void testAssertNotSame()
    {
        Object instance1 = new Object();
        Object instance2 = new Object();
        Assertions.assertNotSame(instance1, instance2, "[Checking if two instances of the same class are not same]");
    }
}
