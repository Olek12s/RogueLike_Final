package world.generation;

import java.util.Random;

public class CaveNegativeTwo
{
    private final int stepSize = 128;
    private final int scale = 128;
    private final int caveThickness = 150;      // value between 0 - 255
    private final int width;
    private final int height;
    private final long seed;
    private short[][] mapValues;
    private short[][] map1;
    private short[][] map2;
    private short[][] map3;
    private DiamondSquare ds1;
    private DiamondSquare ds2;
    private DiamondSquare ds3;

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public CaveNegativeTwo(int width, int height)
    {
        Random random = new Random(System.currentTimeMillis());

        this.width = width;
        this.height = height;
        this.seed = random.nextLong();
        this.mapValues = new short[width][height];

        ds1 = new DiamondSquare(width, height, stepSize/4, scale, 1f, seed);
        ds2 = new DiamondSquare(width, height, stepSize/2, scale*2, 1f, seed);
        ds3 = new DiamondSquare(width, height, stepSize/4, scale, 1f, seed);

        map1 = ds1.getValues();
        map2 = ds2.getValues();
        map3 = ds3.getValues();

        combineValues();
    }

    private void combineValues()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                float val1 = (map1[x][y] * 0.8f);
                float val2 = (map2[x][y] * 0.1f);
                float val3 = (map3[x][y]);
                double val = Math.max(val1-8, ((val1 + val2 + val3)*2.5f)) / 1.6f;

                if (val < 0 || val > caveThickness) val = 0;
                else val = 255;

                mapValues[x][y] = (short)val;
            }
        }
    }
}
