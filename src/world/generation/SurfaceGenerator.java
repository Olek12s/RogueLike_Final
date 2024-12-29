package world.generation;

import java.util.Random;

public class SurfaceGenerator
{
    private final int stepSize = 128;
    private final int scale = 32;
    private final int width;
    private final int height;
    private final long seed;
    private short[][] mapValues;
    short[][] map1;
    short[][] map2;
    short[][] map3;
    private DiamondSquare ds1;
    private DiamondSquare ds2;
    private DiamondSquare ds3;

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public SurfaceGenerator(int width, int height)
    {
        Random random = new Random(System.currentTimeMillis());

        this.width = width;
        this.height = height;
        this.seed = random.nextLong();
        this.mapValues = new short[width][height];

        ds1 = new DiamondSquare(width, height, stepSize*2,  scale-6, 0.90f, seed);
        ds2 = new DiamondSquare(width, height, stepSize/2, scale+8, 0.89f, seed+1);
        ds3 = new DiamondSquare(width, height, stepSize/2, scale/2, 0.90f, seed+1);
        map1 = ds1.getValues();
        map2 = ds2.getValues();
        map3 = ds3.getValues();

        combineValues();
    }

    private double distanceFactorFromEdge(int x, int y)
    {
        double centerX = (width - 1) / 2.0;
        double centerY = (height - 1) / 2.0;
        float strength = 0.97f; // how strong distance factor is

        double dx = x - centerX;
        double dy = y - centerY;
        double dist = Math.sqrt(dx * dx + dy * dy);

        double maxDist = Math.sqrt(centerX/strength * centerX/strength + centerY/strength * centerY/strength);

        double factor = 1.0 - (dist / maxDist);
        if (factor < 0.0) factor = 0.0;
        if (factor > 1.0) factor = 1.0;
        return factor;
    }

    private void combineValues()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                float val1 = map1[x][y] * 0.8f;
                float val2 = map2[x][y] * 0.9f;
                float val3 = map3[x][y] * 0.7f;
                double val = Math.max(val1+8 + 64.0 + val2*2-16, val2) + 64 + val3*3;

                double factor = distanceFactorFromEdge(x, y);
                val = Math.max(val, val2);
                val = val * factor*0.75f;

                if (val < 35) val = 0;
                else if (val > 35 && val < 40) val = 64;
                else if (val > 95 && val < 255) val = 255;

               // if (val < 40) val = 0;                     // water
               //  else if (val > 40 && val < 45) val = 30;       // beach
               //  else if (val > 45 && val < 100) val = 80;      // grass
               //  else if (val > 45) val = 128;


             //  else val = 255;


                mapValues[x][y] = (short)val;
            }
        }
    }
}
