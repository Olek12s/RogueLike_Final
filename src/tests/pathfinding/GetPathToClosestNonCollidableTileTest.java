package tests.pathfinding;

import test.annotations.Test;
import test.runner.Assertions;
import utilities.Pathfinding;
import utilities.Position;

public class GetPathToClosestNonCollidableTileTest
{
    // 0 - non-collidable Dirt
    // 2 - collidable Stone

    @Test
    public void getPathToClosestNonCollidableTileTest1()
    {
        Position start = new Position(0,0);
        short[][] mapValues =   // 5 x 5 matrix
                {
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,0},
                };

        Position[] path = Pathfinding.getPathToClosestNonCollidableTile(mapValues, start);

        Assertions.assertNotNull(path, "Path should not be null");
        Assertions.assertTrue(path.length > 0, "Path should contain at least one step");
        Assertions.assertEquals(new Position(4, 4), Pathfinding.findNearestNonCollidableTile(mapValues, start), "The path should end at the closest non-collidable tile");

        for (int i = 1; i < path.length; i++)
        {
            Position prev = path[i - 1];
            Position curr = path[i];
            Assertions.assertTrue(Math.abs(prev.x - curr.x) + Math.abs(prev.y - curr.y) == 1);
        }
    }

    @Test
    public void getPathToClosestNonCollidableTileTest2()
    {
        Position start = new Position(0,0);
        short[][] mapValues =   // 17 x 2 matrix
                {
                        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
                };

        Position[] path = Pathfinding.getPathToClosestNonCollidableTile(mapValues, start);



        for (int i = 0; i < path.length-1; i++)
        {
            Assertions.assertEquals(new Position(0, i), path[i]);
        }
        Assertions.assertEquals(new Position(1, 16), path[path.length-1]);
    }

    @Test
    public void getPathToClosestNonCollidableTileTest3()
    {
        Position start = new Position(0,0);
        short[][] mapValues =   // 2 x 17 mmatrix
                {
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,2},
                        {2,0},
                };

        Position[] path = Pathfinding.getPathToClosestNonCollidableTile(mapValues, start);

        Assertions.assertEquals(new Position(0, 0), path[0]);
        for (int i = 1; i < path.length-1; i++)
        {
            Assertions.assertEquals(new Position(i-1, 1), path[i]);
        }
    }


    @Test
    public void getPathToClosestNonCollidableTileTest4()
    {
        Position start = new Position(0,0);
        short[][] mapValues =   // 17 x 2 matrix
                {
                        {0,0},
                        {0,0},
                };

        Position[] path = Pathfinding.getPathToClosestNonCollidableTile(mapValues, start);
        Assertions.assertEquals(start, path[0]);
    }


    @Test
    public void getPathToClosestNonCollidableTileWithoutStartEndTest1()
    {
        Position start = new Position(0,0);
        short[][] mapValues =   // 5 x 5 matrix
                {
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,2},
                        {2,2,2,2,0},
                };

        Position[] path = Pathfinding.getPathToClosestNonCollidableTileWithoutStartEnd(mapValues, start);

        Position[] expectedPath =
                {
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3),
                new Position(0, 4),
                new Position(1, 4),
                new Position(2, 4),
                new Position(3, 4)
        };

        Assertions.assertEquals(expectedPath.length, path.length, "Path length should match expected length.");
        for (int i = 0; i < expectedPath.length; i++)
        {
            Assertions.assertEquals(expectedPath[i], path[i], "Position at index " + i + " should match.");
        }
    }
}
