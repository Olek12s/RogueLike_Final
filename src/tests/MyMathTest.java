package tests;

import test.annotations.Before;
import test.annotations.After;
import test.annotations.Test;

public class MyMathTest {
    private MyMath myMath;

    @Before
    public void setUp() {
        myMath = new MyMath();
        System.out.println("Setup complete.");
    }

    @After
    public void tearDown() {
        myMath = null;
        System.out.println("Teardown complete.");
    }

    @Test
    public void testAdd() {
        assert myMath.add(2, 3) == 5 : "2 + 3 should equal 5";
        System.out.println("testAdd passed!!!");
    }

    @Test
    public void testSubtract() {
        assert myMath.subtract(3, 2) == 1 : "3 - 2 should equal 1";
        System.out.println("testSubtract passed.");
    }
}
