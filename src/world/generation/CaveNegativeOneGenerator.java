package world.generation;

import world.map.tiles.TileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CaveNegativeOneGenerator
{
    private final int stepSize = 256;
    private final int scale = 128;
    private final int width;
    private final int height;
    private final long seed;
    private short[][] mapValues;
    short[][] map1;
    short[][] map2;
    private TerrainGenerator ds1;
    private TerrainGenerator ds2;

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public CaveNegativeOneGenerator(int width, int height)
    {
        Random random = new Random(System.currentTimeMillis());

        this.width = width;
        this.height = height;
       // this.seed = random.nextLong();
        this.seed = TerrainGenerator.getSeed();
        this.mapValues = new short[width][height];

        ds1 = new TerrainGenerator(width, height, stepSize/2,  scale/2, 0.82f, seed);
        ds2 = new TerrainGenerator(width, height, stepSize/2, scale/2, 0.89f, seed);
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
                val = Math.clamp(val, 0, 255);
                //mapValues[x][y] = (short)val;

                //TEMP
                if (val <= 35) val = TileManager.TileID.WATER.getId();
                else if (val > 35 && val <= 40) val = TileManager.TileID.SAND.getId();
                else if (val > 40 && val <= 90) val = TileManager.TileID.GRASS.getId();
                else if (val > 90 && val <= 255) val = TileManager.TileID.STONE.getId();
                mapValues[x][y] = (short)val;
                //TEMP

            }
        }
    }

    /**
     * Builder method decorating map object containing raw tiles.
     * Decorates in sequence:
     *
     *
     *  -
     *  -
     *  -
     * After decoration process saves map to the File "resources/maps/CaveNegOne.txt"
     *
     * @param mapWidth - x map Size
     * @param mapHeight - y map Size
     */
    public static void createCaveNegativeOneMap(int mapWidth, int mapHeight)
    {
        CaveNegativeOneGenerator caveNegativeOneGenerator = new CaveNegativeOneGenerator(mapWidth, mapHeight);
        TerrainGenerator.saveGeneratedMapToFile(caveNegativeOneGenerator.getMapValues(), "resources/maps/CaveNegOne.txt");
    }
}
