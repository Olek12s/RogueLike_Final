package world.generation;

import utilities.Position;
import world.map.tiles.TileID;

import java.util.ArrayList;

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


                if (val <= 35) val = TileID.WATER.getId();
                else if (val > 35 && val <= 40) val = TileID.SAND.getId();
                else if (val > 40 && val <= 90) val = TileID.GRASS.getId();
                else if (val > 90 && val <= 255) val = TileID.STONE.getId();
                mapValues[x][y] = (short)val;
            }
        }
        map1 = null;
        map2 = null;
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
        long start = System.currentTimeMillis();
        int negativeOneEnterances = TerrainGenerator.determineCaveEnterancesCount(mapWidth);

        SurfaceGenerator surfaceGenerator = new SurfaceGenerator(mapWidth, mapHeight);
        for (int i = 0; i < negativeOneEnterances; i++)
        {
            Position cave = TerrainGenerator.replaceSpecifiedTileAtRandomPlaceAndCreatePathToMainLand(surfaceGenerator.getMapValues(), TileID.STONE.getId(), TileID.CAVE_ENTRANCE.getId(), TileID.DIRT.getId());
            caveEnterancesPositions.add(cave);
        }
        TerrainGenerator.saveGeneratedMapToFile(surfaceGenerator.getMapValues(), "resources/maps/Surface.txt");
        System.gc();

        long end = System.currentTimeMillis();
        float elapsed = (float)(end-start)/1000;
        System.out.println("\033[0;37m");
        System.out.print("SURFACE: " + elapsed + " / 0.300");
        final String ANSI_GREEN = "\u001B[32m\t";
        final String ANSI_RED = "\u001B[31m\t";
        if (elapsed <= 0.3) System.out.print(ANSI_GREEN + "ZALICZONE");
        else System.out.println(ANSI_RED + "PRZEKROCZONY CZAS");
        System.out.println("\033[0;37m");
    }


}
