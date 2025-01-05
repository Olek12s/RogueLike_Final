package tests;

import test.annotations.Test;
import test.runner.Assertions;
import utilities.Pathfinding;
import utilities.Position;

public class PathfindingTester
{
    // 0 - non-collidable Dirt
    // 2 - collidable Stone

    @Test
    public void findNearestNonCollidableTileTest1()
    {
        Position start;
        Position end;
        short[][] mapValues =   // 5 x 5 matrix
                {
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,0},
                };

        end = new Position(4, 4);

        for (int xs = 0; xs < mapValues.length; xs++)
        {
            for (int ys = 0; ys < mapValues[0].length; ys++)
            {
                if (xs == mapValues.length-1 && ys == mapValues[0].length-1) continue;;
                start = new Position(xs, ys);
                Position answer = Pathfinding.findNearestNonCollidableTile(mapValues, start);
                Assertions.assertEquals(end, answer);
            }
        }
    }

    @Test
    public void findNearestNonCollidableTileTest2()
    {
        Position start;
        Position expectedEnd;
        short[][] mapValues =   // 5 x 5 matrix
                {
                        {0,2,2,2,0},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {0,2,2,2,0},
                };

        start = new Position(0,0);
        expectedEnd = new Position(0, 4);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));

        // left-up start
        start = new Position(1,1);
        expectedEnd = new Position(0, 0);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));

        // right-up start
        start = new Position(3,0);
        expectedEnd = new Position(4, 0);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));

        // left-down start
        start = new Position(0,3);
        expectedEnd = new Position(0, 4);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));

        // right-down start
        start = new Position(3,3);
        expectedEnd = new Position(4, 4);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
    }

    @Test
    public void findNearestNonCollidableTileTest3()
    {
        Position start;
        Position expectedEnd;
        short[][] mapValues =   // 10 x 10 matrix
                {
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {2,2,0,0,0,0,0,0,0,0},
                        {2,2,0,0,0,0,0,0,0,0},
                        {2,2,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0}
                };

        start = new Position(0,0);
        expectedEnd = new Position(0, 1);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));

        start = new Position(0,5);
        expectedEnd = new Position(0, 3);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));

        mapValues[3][0] = 2;
        Assertions.assertNotEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
        expectedEnd = new Position(0, 7);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
        mapValues[7][0] = 2;
        Assertions.assertNotEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
        expectedEnd = new Position(2, 5);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
        mapValues[5][2] = 2;
        Assertions.assertNotEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
        expectedEnd = new Position(0, 2);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
        mapValues[2][0] = 2;
        Assertions.assertNotEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                mapValues[i][j] = 2;
            }
        }
        mapValues[8][8] = 0;
        expectedEnd = new Position(8, 8);
        Assertions.assertEquals(expectedEnd, Pathfinding.findNearestNonCollidableTile(mapValues, start));
    }
}
