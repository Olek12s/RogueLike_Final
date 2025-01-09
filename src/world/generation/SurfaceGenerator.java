package world.generation;

import main.GameController;
import utilities.Position;
import world.map.MapController;
import world.map.MapLevels;
import world.map.tiles.Tile;
import world.map.tiles.TileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SurfaceGenerator
{
    // GENERATOR VARIABLES
    private final int stepSize = 128;
    private final int scale = 32;
    private final int width;
    private final int height;
    private final long seed;
    private short[][] mapValues;
    short[][] map1;
    short[][] map2;
    short[][] map3;
    private TerrainGenerator ds1;
    private TerrainGenerator ds2;
    private TerrainGenerator ds3;
    // GENERATOR VARIABLES

    private static ArrayList<Position> caveEnterancesPositions = new ArrayList<>();

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public static int getCaveEnterancesCount() {return caveEnterancesPositions.size();}
    public static ArrayList<Position> getCaveEnterancesPositions() {return caveEnterancesPositions;}

    public SurfaceGenerator(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.seed = TerrainGenerator.getSeed();
        this.mapValues = new short[width][height];

        ds1 = new TerrainGenerator(width, height, stepSize*2,  scale-6, 0.90f, seed);
        ds2 = new TerrainGenerator(width, height, stepSize/2, scale+8, 0.89f, seed+1);
        ds3 = new TerrainGenerator(width, height, stepSize/2, scale/2, 0.90f, seed+1);
        map1 = ds1.getValues();
        map2 = ds2.getValues();
        map3 = ds3.getValues();

        combineValues();
    }

    private double distanceFactorFromEdge(int x, int y)
    {
        double centerX = (width - 1) / 2.0;
        double centerY = (height - 1) / 2.0;
        float strength = 1.07f; // how strong distance factor is

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

                /*
                if (val < 35) val = 0;
                else if (val > 35 && val < 40) val = 64;
                else if (val > 95 && val < 255) val = 255;
                */


                if (val <= 35) val = TileManager.TileID.WATER.getId();
                else if (val > 35 && val <= 40) val = TileManager.TileID.SAND.getId();
                else if (val > 40 && val <= 90) val = TileManager.TileID.GRASS.getId();
                else if (val > 90 && val <= 255) val = TileManager.TileID.STONE.getId();
                mapValues[x][y] = (short)val;
            }
        }
    }

    /**
     * Builder method decorating map object containing raw tiles.
     * Decorates in sequence:
     *  -Creates cave enterances
     *  -Creates dirt paths to the closest non-collidable Tile
     *  -
     *  -
     *  -
     * After decoration process saves map to the File "resources/maps/Surface.txt"
     *
     * @param mapWidth - x map Size
     * @param mapHeight - y map Size
     */
    public static void createSurfaceMap(int mapWidth, int mapHeight)
    {
        int negativeOneEnterances = determineNegativeOneEnterancesCount(mapWidth);

        SurfaceGenerator surfaceGenerator = new SurfaceGenerator(mapWidth, mapHeight);
        for (int i = 0; i < negativeOneEnterances; i++)
        {
            Position cave = TerrainGenerator.replaceSpecifiedTileAtRandomPlaceAndCreatePath(surfaceGenerator.getMapValues(), TileManager.TileID.STONE.getId(), TileManager.TileID.CAVE_ENTRANCE.getId(), TileManager.TileID.DIRT.getId());
            caveEnterancesPositions.add(cave);
        }
        MapController.createMap(MapLevels.SURFACE.getValue(), mapWidth, mapHeight);
        TerrainGenerator.saveGeneratedMapToFile(surfaceGenerator.getMapValues(), "resources/maps/Surface.txt");
    }


    /**
     *  calculates proper negative one cave enterances count with formula:
     *  1024 - 8 enterances
     *  512 - 3 enterances
     *  256 - 1 enterances
     *
     * @param mapSize
     * @return
     */
    private static int determineNegativeOneEnterancesCount(int mapSize)
    {
        if (mapSize == 1024) return 80;
        else if (mapSize == 512) return 3;
        else if (mapSize == 256) return 1;
        else throw new IllegalArgumentException("Illegal map size");
    }
}
