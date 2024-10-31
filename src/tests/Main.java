package tests;

import test.annotations.Test;
import test.runner.TestRunner;

public class Main
{
    public static void main(String[] args)
    {
        TestRunner.runTests(TestTest.class);
        TestRunner.runTests(SpriteTest.class);
        TestRunner.printSummary();
    }
}
