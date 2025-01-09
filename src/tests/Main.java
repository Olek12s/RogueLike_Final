package tests;

import test.runner.TestRunner;
import tests.pathfinding.FindNearestNonCollidableTileTester;
import tests.pathfinding.GetPathToClosestNonCollidableTileTest;
import tests.pathfinding.GetPathToMainLandTester;

public class Main
{
    public static void main(String[] args)
    {

        TestRunner.printSummary();
        TestRunner.runTests(EntityTester.class);
        TestRunner.runTests(TerrainGeneratorBFSTester.class);
        TestRunner.runTests(FindNearestNonCollidableTileTester.class);
        TestRunner.runTests(GetPathToClosestNonCollidableTileTest.class);
        TestRunner.runTests(GetPathToMainLandTester.class);
    }
}
