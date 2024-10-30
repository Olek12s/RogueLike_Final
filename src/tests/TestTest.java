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
        Assertions.assertEquals(5, myMath.add(2, 3));
    }

    @Test
    public void testSubtract()
    {
        Assertions.assertEquals(2, myMath.subtract(3, 2), "XDDDDDD", "XDDASD");
    }

    @Test
    public void testAssertFalse()
    {
        Assertions.assertFalse(myMath.add(1, 1) == 2);
    }

    @Test
    public void testAssertTrue()
    {
        Assertions.assertTrue(myMath.add(1, 1) == 3);
    }

    @Test
    public void testAssertNotNull()
    {
        Assertions.assertNotNull(myMath);
    }

    @Test
    public void testAssertNull()
    {
        tearDown();
        Assertions.assertNull(myMath);
    }

    @Test
    public void testAssertNotEquals()
    {
        Assertions.assertNotEquals(3, myMath.add(2, 2));
    }

    @Test
    public void testAssertSame()
    {
        MyMath instance = myMath;
        Assertions.assertSame(myMath, instance);
    }

    @Test
    public void testAssertNotSame()
    {
        MyMath instance = new MyMath();
        Assertions.assertNotSame(myMath, instance);
    }
}
