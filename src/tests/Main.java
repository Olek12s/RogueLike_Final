package tests;

import main.entity.Entity;
import test.runner.TestRunner;

public class Main
{
    public static void main(String[] args)
    {

        TestRunner.printSummary();
        TestRunner.runTests(EntityTester.class);
    }
}
