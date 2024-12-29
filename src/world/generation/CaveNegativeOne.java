package world.generation;

import java.util.Random;

public class CaveNegativeOne
{
    private final int stepSize = 256;
    private final int scale = 128;
    private final int width;
    private final int height;
    private final long seed;
    private short[][] mapValues;
    short[][] map1;
    short[][] map2;
    private DiamondSquare ds1;
    private DiamondSquare ds2;

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public CaveNegativeOne(int width, int height)
    {
        Random random = new Random(System.currentTimeMillis());

        this.width = width;
        this.height = height;
        this.seed = random.nextLong();
        this.mapValues = new short[width][height];

        ds1 = new DiamondSquare(width, height, stepSize/2,  scale/2, 0.82f, seed);
        ds2 = new DiamondSquare(width, height, stepSize/2, scale/2, 0.89f, seed);
        map1 = ds1.getValues();
        map2 = ds2.getValues();

        combineValues();
    }

    private void combineValues()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                float val1 = map1[x][y];
                float val2 = map2[x][y];

                double val = (((val1+150) * val1-170) % 190) * 5;
                val = Math.min(val, val2);
                val = (val * 2 + 190);

                mapValues[x][y] = (short)val;
            }
        }
    }
}
