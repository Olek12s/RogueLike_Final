package tests;

import test.runner.TestRunner;

public class Main
{
    public static void main(String[] args)
    {
        TestRunner.runTests(TestTest.class);
        TestRunner.runTests(SpriteSheetTest.class);
        TestRunner.runTests(EntityTest.class);
        TestRunner.runTests(CollisionTest.class);
        TestRunner.runTests(MapTest.class);
        TestRunner.printSummary();
    }
}
