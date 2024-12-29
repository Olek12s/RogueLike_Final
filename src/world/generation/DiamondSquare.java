package world.generation;

import java.util.Random;

public class DiamondSquare
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


    public DiamondSquare(int width, int height, int stepSize, double scale, float bias, long seed)
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
}
