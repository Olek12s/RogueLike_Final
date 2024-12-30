package world.generation;

import utilities.Position;

import java.util.*;

// Diamond-Square method
public class TerrainGenerator
{
    private int stepSize;
    private int width;
    private int height;
    private double scale;
    private short[][] values;
    private long seed;
    private float bias;

    public void setSeed(long seed) {this.seed = seed;}
    public long getSeed() {return seed;}


    public TerrainGenerator(int width, int height, int stepSize, double scale, float bias, long seed)
    {
        this.values = new short[width+1][height+1];
        this.width = width+1;
        this.height = height+1;
        this.stepSize = stepSize;
        this.scale = scale;
        this.bias = bias;
        this.seed = seed;

        values[0][0] = -1;
        values[0][height - 1] = -1;
        values[width - 1][0] = -1;
        values[width - 1][height - 1] = -1;

        generateDiamondSquareHeightMap();
    }

    public void generateDiamondSquareHeightMap()
    {
        while (stepSize > 1)
        {
            diamondStep();
            squareStep();

            scale /= 2;
            stepSize /= 2;
        }
    }

    private void diamondStep()
    {
        int halfStep = stepSize/2;
        Random random = new Random(seed);

        for (int x = halfStep; x < width; x += stepSize)
        {
            for (int y = halfStep; y < height; y += stepSize)
            {
                float topLeft     = values[x - halfStep][y - halfStep];
                float topRight    = values[x + halfStep][y - halfStep];
                float bottomLeft  = values[x - halfStep][y + halfStep];
                float bottomRight = values[x + halfStep][y + halfStep];

                float generatedValue = (topLeft + topRight + bottomLeft + bottomRight) / 4.0f;
                generatedValue += (random.nextFloat() * 2 - bias) * (float)scale;    // bias

                values[x][y]  = (short)generatedValue;
            }
        }
    }

    private void squareStep()
    {
        int halfStep = stepSize/2;
        Random random = new Random(seed);

        for (int x = 0; x < width; x += halfStep)
        {
            for (int y = (x + halfStep) % stepSize; y < height; y += stepSize)
            {
                float generatedValue = 0;
                int count = 0;

                // Left neigh (x - half, y)
                if (x - halfStep >= 0)
                {
                    generatedValue += values[x - halfStep][y];
                    count++;
                }

                // Right neigh (x + half, y)
                if (x + halfStep < width)
                {
                    generatedValue += values[x + halfStep][y];
                    count++;
                }

                // Top neigh (x, y - half)
                if (y - halfStep >= 0)
                {
                    generatedValue += values[x][y - halfStep];
                    count++;
                }

                // Bottom neigh (x, y + half)
                if (y + halfStep < height)
                {
                    generatedValue += values[x][y + halfStep];
                    count++;
                }

                // avg all values
                if (count > 0)
                {
                    generatedValue /= (float) count;
                }

                generatedValue += ((random.nextFloat() * 2 - bias)*(float)scale);
                values[x][y] = (short)generatedValue;
            }
        }
    }

    public short[][] getValues()
    {
        generateDiamondSquareHeightMap();
        return values;
    }

    //  Breadth-First Search (BFS)
    public static boolean doesGraphPathExists(short[][] values, short[] nonPassableValues, Position start, Position end)
    {
        // O(1) checking set
        Set<Short> nonPassableSet = new HashSet<>();
        for(short val : nonPassableValues)
        {
            nonPassableSet.add(val);
        }

        if ((start.x == end.x) && (start.y == end.y)) return false;

        // if start or end are within grid
        if(start.x < 0 || start.x >= values.length
                || start.y < 0 || start.y >= values[0].length
                || end.x < 0 || end.x >= values.length || end.y < 0 || end.y >= values[0].length)
        {
            return false;
        }


        // if start or end points are not passable
        if(nonPassableSet.contains(values[start.y][start.x]) || nonPassableSet.contains(values[end.y][end.x]))
        {
            return false;
        }

        // BFS queue and visited array
        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[values.length][values[0].length];

        queue.add(start);
        visited[start.y][start.x] = true;
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };  // we care only about side neighbours

        // BFS

        while(!queue.isEmpty())
        {
            Position current = queue.poll();

            // if end point is reached
            if(current.x == end.x && current.y == end.y)
            {
                return true;
            }

            // Checking neighbours
            for(int[] dir : directions)
            {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                // is neighbour within grid
                if(newX >= 0 && newX < values.length && newY >= 0 && newY < values[0].length)
                {
                    // checking if neighbour is not in nonPassableSet and was not visited
                    if(!nonPassableSet.contains(values[newX][newY]) && !visited[newX][newY])
                    {
                        queue.add(new Position(newX, newY));
                        visited[newX][newY] = true;
                    }
                }
            }
        }
        // return false if we did not find path
        return false;
    }
}
