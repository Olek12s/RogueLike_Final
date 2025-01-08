package world.generation;

import utilities.Position;
import world.map.tiles.TileManager;

import java.util.ArrayList;

public class CaveNegativeTwoGenerator
{
    //GENERATOR VARIABLES
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
    //GENERATOR VARIABLES

    public static ArrayList<Position> caveNegTwoExits = new ArrayList<>();

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public CaveNegativeTwoGenerator(int width, int height)
    {

        this.width = width;
        this.height = height;
       // this.seed = random.nextLong();
        this.seed = TerrainGenerator.getSeed();
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

                //mapValues[x][y] = (short)val;

                if (val >= 170) mapValues[x][y] = (short) TileManager.TileID.BASALT_FLOOR.getId();
                else mapValues[x][y] = (short) TileManager.TileID.CAVE_FLOOR.getId();

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
     * After decoration process saves map to the File "resources/maps/CaveNegTwo.txt"
     *
     * @param mapWidth - x map Size
     * @param mapHeight - y map Size
     */
    public static void createCaveNegativeTwoMap(int mapWidth, int mapHeight)
    {
        CaveNegativeTwoGenerator caveNegativeTwoGenerator = new CaveNegativeTwoGenerator(mapWidth, mapHeight);

        for (int i = 0; i < SurfaceGenerator.getCaveEnterancesCount(); i++) // generate cave exits
        {
            // replace creating path with creating path to closest rooms with x non-collidable tiles
            TerrainGenerator.replaceSpecifiedTileAtSpecifiedPlaceAndCreatePath(caveNegativeTwoGenerator.getMapValues(), TileManager.TileID.CAVE_DEEP_EXIT.getId(), TileManager.TileID.BASALT_FLOOR.getId(), CaveNegativeOneGenerator.getCaveNegTwoEnterances().get(i));
            caveNegTwoExits.add(SurfaceGenerator.getCaveEnterancesPositions().get(i));
        }
        TerrainGenerator.saveGeneratedMapToFile(caveNegativeTwoGenerator.getMapValues(), "resources/maps/CaveNegTwo.txt");
    }
}
