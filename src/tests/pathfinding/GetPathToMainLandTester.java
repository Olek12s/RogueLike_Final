package tests.pathfinding;

import test.annotations.Test;
import test.runner.Assertions;
import utilities.Pathfinding;
import utilities.Position;

import static utilities.Pathfinding.*;

public class GetPathToMainLandTester
{
    // 0 - non-collidable Dirt
    // 2 - collidable Stone



    @Test
    public void getPathToMainLandTest1()
    {
        Position start = new Position(5,5); // middle of matrix
        short[][] mapValues =   // 11 x 11 matrix
                {
                        {0,0,2,2,2,2,2,2,2,2,2},
                        {0,0,2,2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2,2,2,2},
                        {0,2,2,2,2,2,2,2,2,2,2},
                        {0,2,2,2,2,2,2,2,2,2,2},
                        {0,2,2,2,2,2,0,2,2,2,2},
                        {0,2,2,2,2,2,2,0,2,2,2},
                        {0,2,2,2,2,2,2,2,0,2,2},
                        {0,2,2,2,2,2,2,2,2,0,2},
                };

        Position[] pathNull = getPathToMainLand(mapValues, start, 7);
        Position[] pathLeft = getPathToMainLand(mapValues, start, 6);
        Position[] pathRightDown = getPathToMainLand(mapValues, start, 1);

        Position[] pathLeftAnswer = new Position[]{
                new Position(5,5),
                new Position(4,5),
                new Position(3,5),
                new Position(2,5),
                new Position(1,5),
                new Position(0,5)
        };
        Position[] pathRightDownAnswer = new Position[]{
                new Position(5,5),
                new Position(5,6),
                new Position(5,7),
                new Position(6,7)
        };

        Assertions.assertNull(pathNull);
        for (int i = 0; i < pathLeftAnswer.length; i++) Assertions.assertEquals(pathLeft[i], pathLeftAnswer[i]);
        for (int i = 0; i < pathRightDownAnswer.length; i++) Assertions.assertEquals(pathRightDown[i], pathRightDownAnswer[i]);

    }
}
