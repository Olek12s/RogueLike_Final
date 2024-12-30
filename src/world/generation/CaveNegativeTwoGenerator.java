package world.generation;

import java.util.Random;

public class CaveNegativeTwoGenerator
{
    private final int stepSize = 128;
    private final int scale = 128;
    private final int width;
    private final int height;
    private final long seed;
    private short[][] mapValues;
    private short[][] map1;
    private short[][] map2;
    private TerrainGenerator ds1;
    private TerrainGenerator ds2;

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public CaveNegativeTwoGenerator(int width, int height)
    {
        Random random = new Random(System.currentTimeMillis());

        this.width = width;
        this.height = height;
        this.seed = random.nextLong();
        this.mapValues = new short[width][height];

        ds1 = new TerrainGenerator(width, height, stepSize/4, scale, 1f, seed);
        ds2 = new TerrainGenerator(width, height, stepSize/2, scale*2, 1f, seed);

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
                float val1 = (map1[x][y] * 0.8f);
                float val2 = (map2[x][y] * 0.1f);
                double val = Math.clamp(Math.abs(val1 + val2 * 20.0),0,255);
                val = 255.0 - val;  // reverse
                //if (val < 0 || val > caveThickness) val = 255;
                //else val = 0;

                mapValues[x][y] = (short)val;

            }
        }
    }
}
