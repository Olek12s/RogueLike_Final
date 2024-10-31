package tests;

import test.annotations.*;
import test.runner.*;

public class TestTest
{
    private MyMath myMath;

    @Before
    public void setUp()
    {
        myMath = new MyMath();
    }

    @After
    public void tearDown()
    {
        myMath = null;
    }

    @Test
    public void testAdd()
    {
        Assertions.assertEquals(5, myMath.add(2, 3), "[Adding 2 digits]");
    }

    @Test
    public void testSubtract()
    {
        Assertions.assertEquals(2, myMath.subtract(3, 2), "[Subtracting 2 digits]");
    }

    @Test
    public void testAssertFalse()
    {
        Assertions.assertFalse(myMath.add(1, 1) == 2, "[Comparing, if 1+1 = 2 isn't two");
    }

    @Test
    public void testAssertTrue()
    {
        Assertions.assertTrue(myMath.add(1, 1) == 3, "[Comparing, if 1+1 = 3 is true");
    }

    @Test
    public void testAssertNotNull()
    {
        Assertions.assertNotNull(myMath, "[Checking if myMath object exists]");
    }

    @Test
    public void testAssertNull()
    {
        tearDown();
        Assertions.assertNull(myMath, "[Checking if myMath object does not exist]");
    }

    @Test
    public void testAssertNotEquals()
    {
        Assertions.assertNotEquals(3, myMath.add(2, 2), "[Checking, if 2+2 equals 3]");
    }

    @Test
    public void testAssertSame()
    {
        MyMath instance = myMath;
        Assertions.assertSame(myMath, instance, "[Checking if two instances of the same class are same]");
    }

    @Test
    public void testAssertNotSame()
    {
        MyMath instance = new MyMath();
        Assertions.assertNotSame(myMath, instance, "[Checking if two instances of the same class are not same]");
    }
}
