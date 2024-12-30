package tests;

import test.annotations.Test;
import test.runner.Assertions;
import utilities.Position;
import world.generation.TerrainGenerator;

public class TerrainGeneratorBFSTester
{
    @Test
    public void doesPathExist()
    {
        short[][] grid =
                {
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1}
        };
        short[] nonPassable = {0};

        // * * * * * * * * * * * * * //
        // DIFFERENT STARTING POINTS //
        // * * * * * * * * * * * * * //

        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(4, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(1, 0), new Position(4, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(2, 0), new Position(4, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(3, 0), new Position(4, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(4, 0), new Position(4, 4))); // expected true

        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 1), new Position(4, 4))); // expected true
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(1, 1), new Position(4, 4))); // expected false
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(2, 1), new Position(4, 4))); // expected false
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(3, 1), new Position(4, 4))); // expected false
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(4, 1), new Position(4, 4))); // expected true

        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(4, 4), new Position(4, 4))); // expected false (same position)
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(4, 4), new Position(-1, 4))); // expected false (out of bonds)
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(4, 4), new Position(4, -1))); // expected false (out of bonds)
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(4, 4), new Position(5, 5))); // expected false (out of bonds)

        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(3, 4), new Position(4, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(3, 4), new Position(4, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(4, 0), new Position(4, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 4), new Position(4, 4))); // expected true


        // * * * * * * * * * * * * * //
        //  DIFFERENT ENDING POINTS  //
        // * * * * * * * * * * * * * //

        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(0, 0))); // expected false (same position)
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(1, 0))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(2, 0))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(3, 0))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(4, 0))); // expected true


        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(0, 1))); // expected true
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(1, 2))); // expected false
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(2, 3))); // expected false
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(3, 4))); // expected true
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(4, 4))); // expected true
    }

    @Test
    public void doesPathExist2()
    {
        short[][] grid =
                {
                        {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                };
        short[] nonPassable = {0};

        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(0, 2)));
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(5, 0), new Position(6, 1)));
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(11, 1), new Position(11, 0)));

        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(10, 0)));
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(1, 0)));
        Assertions.assertFalse(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(2, 0)));
    }

    @Test
    public void doesPathExist3() {
        short[][] grid =
                {
                        {1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                        {1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1}
                };
        short[] nonPassable = {0};
        Assertions.assertTrue(TerrainGenerator.doesGraphPathExists(grid, nonPassable, new Position(0, 0), new Position(11, 2)));
    }

    @Test
    public void doesPathExist4()
    {
        short[][] grid =
                {
                        {1, 1},
                        {1, 1},

                };
        short[] nonPassable = {0};
    }

    @Test
    public void doesPathExist5()
    {
        short[][] grid =
                {
                        {1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1}
                };
        short[] nonPassable = {0};
    }

    @Test
    public void doesPathExist6()
    {
        short[][] grid =
                {
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                };
        short[] nonPassable = {0};
    }
}
