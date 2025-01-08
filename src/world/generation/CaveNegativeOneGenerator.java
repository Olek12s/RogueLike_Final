package world.generation;

import utilities.Position;
import world.map.MapController;
import world.map.tiles.TileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CaveNegativeOneGenerator
{
    //GENERATOR VARIABLES
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
    //GENERATOR VARIABLES

    public static ArrayList<Position> caveExitsPositions = new ArrayList<>();

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public CaveNegativeOneGenerator(int width, int height)
    {
        Random random = new Random(System.currentTimeMillis());

        this.width = width;
        this.height = height;
        //this.seed = random.nextLong();
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
                mapValues[x][y] = (short)val;

                //if (val > 125 && val <= 150) val = 255;

                /* VISUALIZATION
                if (mapValues[x][y] >= 250) val = 255;                              //   non-collidable  <250 255>
                else if (mapValues[x][y] >= 240 && mapValues[x][y] <= 243) val = 125;    // GRAVEl <240 243>
                else if (mapValues[x][y] >= 215 && mapValues[x][y] <= 245) val = 30;    // non-collidable <215 245>
                else if (mapValues[x][y] >= 190 && mapValues[x][y] <= 195) val = 125;    // GRAVEl <190 195>
                else if (mapValues[x][y] >= 195 && mapValues[x][y] <= 200) val = 30;    // non-collidable <195 200>
                else if (mapValues[x][y] >= 200 && mapValues[x][y] <= 206) val = 125;    // GRAVEl <200 205>
                else val = 0;   // STONE
                mapValues[x][y] = (short)val;
                VISUALIZATION */

                if (mapValues[x][y] >= 250) val = TileManager.TileID.CAVE_FLOOR.getId();
                else if (mapValues[x][y] >= 240 && mapValues[x][y] <= 243) val = TileManager.TileID.GRAVEL.getId();
                else if (mapValues[x][y] >= 215 && mapValues[x][y] <= 245) val = TileManager.TileID.CAVE_FLOOR.getId();
                else if (mapValues[x][y] >= 190 && mapValues[x][y] <= 195) val = TileManager.TileID.GRAVEL.getId();
                else if (mapValues[x][y] >= 195 && mapValues[x][y] <= 200) val = TileManager.TileID.CAVE_FLOOR.getId();
                else if (mapValues[x][y] >= 200 && mapValues[x][y] <= 206) val = TileManager.TileID.GRAVEL.getId();
                else val = TileManager.TileID.ROCK.getId();
                mapValues[x][y] = (short)val;
            }
        }
    }

    /**
     * Builder method decorating map object containing raw tiles.
     * Decorates in sequence:
     *  -Generates tiles
     *  -Generates cave exits depending on the quantity of the cave enterances level above
     *  -Generates caveNegTwo enterances
     *  -
     *  -
     * After decoration process saves map to the File "resources/maps/CaveNegOne.txt"
     *
     * @param mapWidth - x map Size
     * @param mapHeight - y map Size
     */
    public static void createCaveNegativeOneMap(int mapWidth, int mapHeight)
    {
        System.err.println("C");
        CaveNegativeOneGenerator caveNegativeOneGenerator = new CaveNegativeOneGenerator(mapWidth, mapHeight);
        for (int i = 0; i < SurfaceGenerator.getCaveEnterancesCount(); i++) // generate cave exits
        {
            // replace creating path with creating path to closest rooms with x non-collidable tiles
            TerrainGenerator.replaceSpecifiedTileAtSpecifiedPlaceAndCreatePath(caveNegativeOneGenerator.getMapValues(), TileManager.TileID.CAVE_EXIT.getId(), TileManager.TileID.CAVE_FLOOR.getId(), SurfaceGenerator.getCaveEnterancesPositions().get(i));
            caveExitsPositions.add(SurfaceGenerator.getCaveEnterancesPositions().get(i));
        }
        TerrainGenerator.saveGeneratedMapToFile(caveNegativeOneGenerator.getMapValues(), "resources/maps/CaveNegOne.txt");
    }
}






















