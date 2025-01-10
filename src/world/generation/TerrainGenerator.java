package world.generation;



import main.GameController;
import utilities.Pathfinding;
import utilities.Position;
import world.map.tiles.TileManager;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

// Diamond-Square method
public class TerrainGenerator {
    // GENERATOR VARIABLES
    private int stepSize;
    private int width;
    private int height;
    private double scale;
    private short[][] values;
    private static Random rSeed = new Random(System.currentTimeMillis());
    private static long seed = rSeed.nextLong();
    private float bias;
    static Random random;
    // GENERATOR VARIABLES


    public void setSeed(long seed) {
        this.seed = seed;
    }

    public static long getSeed() {
        return seed;
    }


    public TerrainGenerator(int width, int height, int stepSize, double scale, float bias, long seed) {
        this.values = new short[width + 1][height + 1];
        this.width = width + 1;
        this.height = height + 1;
        this.stepSize = stepSize;
        this.scale = scale;
        this.bias = bias;
        this.seed = seed;
        this.random = new Random(seed);

        values[0][0] = -1;
        values[0][height - 1] = -1;
        values[width - 1][0] = -1;
        values[width - 1][height - 1] = -1;

        generateDiamondSquareHeightMap();
    }

    public void generateDiamondSquareHeightMap() {
        while (stepSize > 1) {
            diamondStep();
            squareStep();

            scale /= 2;
            stepSize /= 2;
        }
    }

    private void diamondStep() {
        int halfStep = stepSize / 2;

        for (int x = halfStep; x < width; x += stepSize) {
            for (int y = halfStep; y < height; y += stepSize) {
                float topLeft = values[x - halfStep][y - halfStep];
                float topRight = values[x + halfStep][y - halfStep];
                float bottomLeft = values[x - halfStep][y + halfStep];
                float bottomRight = values[x + halfStep][y + halfStep];

                float generatedValue = (topLeft + topRight + bottomLeft + bottomRight) / 4.0f;
                generatedValue += (random.nextFloat() * 2 - bias) * (float) scale;    // bias

                values[x][y] = (short) generatedValue;
            }
        }
    }

    private void squareStep() {
        int halfStep = stepSize / 2;

        for (int x = 0; x < width; x += halfStep) {
            for (int y = (x + halfStep) % stepSize; y < height; y += stepSize) {
                float generatedValue = 0;
                int count = 0;

                // Left neigh (x - half, y)
                if (x - halfStep >= 0) {
                    generatedValue += values[x - halfStep][y];
                    count++;
                }

                // Right neigh (x + half, y)
                if (x + halfStep < width) {
                    generatedValue += values[x + halfStep][y];
                    count++;
                }

                // Top neigh (x, y - half)
                if (y - halfStep >= 0) {
                    generatedValue += values[x][y - halfStep];
                    count++;
                }

                // Bottom neigh (x, y + half)
                if (y + halfStep < height) {
                    generatedValue += values[x][y + halfStep];
                    count++;
                }

                // avg all values
                if (count > 0) {
                    generatedValue /= (float) count;
                }

                generatedValue += ((random.nextFloat() * 2 - bias) * (float) scale);
                values[x][y] = (short) generatedValue;
            }
        }
    }

    public short[][] getValues() {
        generateDiamondSquareHeightMap();
        return values;
    }

    //  Breadth-First Search (BFS)
    public static boolean doesGraphPathExists(short[][] values, short[] nonPassableValues, Position start, Position end) {
        // O(1) checking set
        Set<Short> nonPassableSet = new HashSet<>();
        for (short val : nonPassableValues) {
            nonPassableSet.add(val);
        }

        // if start and end are the same coordinates
        if (start.x == end.x && start.y == end.y) return false;

        // if start or end are not within grid
        if (!isInsideGrid(values, start) || !isInsideGrid(values, end)) {
            return false;
        }

        // if start or end points are on non passables
        if ((nonPassableSet.contains(values[start.y][start.x]) || nonPassableSet.contains(values[end.y][end.x]))) {
            return false;
        }

        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[values.length][values[0].length];

        queue.add(start);
        visited[start.y][start.x] = true;

        // Directions: right, down, left, up
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        // BFS loop
        while (!queue.isEmpty()) {
            Position current = queue.poll(); // get head element and remove it

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                Position neighbor = new Position(newX, newY);

                if (neighbor.x == end.x && neighbor.y == end.y) {
                    return true;
                }

                if (isInsideGrid(values, neighbor) &&
                        !visited[newY][newX] &&
                        !nonPassableSet.contains(values[newY][newX])) {
                    visited[newY][newX] = true;
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    private static boolean isInsideGrid(short[][] grid, Position p) {
        return (p.x >= 0 && p.x < grid[0].length
                && p.y >= 0 && p.y < grid.length);
    }


    /* The fastest method using Java serialization */
    public static void saveGeneratedMapToFile(short[][] mapValues, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(mapValues);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static Position replaceSpecifiedTileAtRandomPlaceAndCreatePath(short[][] mapValues, int replaceTileID, int replaceWithTileID, int pathTileID)
    {
        Position start = replaceSpecifiedTileAtRandomPlace(mapValues, replaceTileID, replaceWithTileID);
        Position[] path = Pathfinding.getPathToClosestNonCollidableTileWithoutStartEnd(mapValues, start);

        for (int i = 0; i < path.length; i++)
        {
            mapValues[path[i].x][path[i].y] = (short) pathTileID;
        }
        return start;
    }

    /**
     *
     *
     * @param mapValues
     * @param replaceWithTileID
     * @param pathTileID
     * @param positionToReplace
     */
    public static void replaceSpecifiedTileAtSpecifiedPlaceAndCreatePath(short[][] mapValues, int replaceWithTileID, int pathTileID, Position positionToReplace)
    {
         replaceSpecifiedTileAtSpecifiedPlace(mapValues, positionToReplace, replaceWithTileID);
         Position[] path = Pathfinding.getPathToClosestNonCollidableTileWithoutStartEnd(mapValues, positionToReplace);

         for (int i = 0; i < path.length; i++)
         {
             mapValues[path[i].x][path[i].y] = (short) pathTileID;
         }
    }

    public static void replaceSpecifiedTileAtSpecifiedPlaceAndCreatePathToMainLand(short[][] mapValues, int replaceWithTileID, int pathTileID, Position positionToReplace)
    {
        replaceSpecifiedTileAtSpecifiedPlace(mapValues, positionToReplace, replaceWithTileID);
        Position[] path = Pathfinding.getPathToMainLandWithoutStartEnd(mapValues, positionToReplace);

        for (int i = 0; i < path.length; i++)
        {
            mapValues[path[i].x][path[i].y] = (short) pathTileID;
        }
    }


    public static void replaceSpecifiedTileAtSpecifiedPlace(short[][] mapValues, Position positionToReplace, int replaceWIthTileID)
    {
        mapValues[positionToReplace.x][positionToReplace.y] = (short) replaceWIthTileID;
    }


    public static Position replaceSpecifiedTileAtRandomPlace(short[][] mapValues, int replaceTileID, int replaceWithTileID)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;


        List<int[]> matchingPositions = new ArrayList<>();    // all positions maching replaceTileID value

        try
        {
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    if (mapValues[x][y] == replaceTileID)
                    {
                        matchingPositions.add(new int[]{x, y});
                    }
                }
            }

            if (matchingPositions.isEmpty())
            {
                throw new Exception("No positions found with the specified replaceTileID: " + replaceTileID);
            }
            int randomIndex = random.nextInt(matchingPositions.size());
            int[] position = matchingPositions.get(randomIndex);

            mapValues[position[0]][position[1]] = (short) replaceWithTileID;
            return new Position(position[0], position[1]);
        }
        catch (Exception ex)    // no positions
        {
            System.err.println("Error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public static void addSingleTileAtRandomPlace(short[][] mapValues, int tileID, int numberOfTilesToPlace)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;


        for (int i = 0; i < numberOfTilesToPlace; i++)
        {
            int randomWidth = random.nextInt(width);
            int randomHeight = random.nextInt(height);
            mapValues[randomWidth][randomHeight] = (short)tileID;
        }
    }

    public static void addSingleTileAtRandomPlace(short[][] mapValues, int tileID)
    {
        addSingleTileAtRandomPlace(mapValues, tileID,1);
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
    public static int determineCaveEnterancesCount(int mapSize)
    {
        if (mapSize == 1024) return 80;
        else if (mapSize == 512) return 3;
        else if (mapSize == 256) return 1;
        else throw new IllegalArgumentException("Illegal map size");
    }

    public static Position replaceSpecifiedTileAtRandomPlaceAndCreatePathToMainLand(short[][] mapValues, int replaceTileID, int replaceWithTileID, int pathTileID)
    {
        Position start = replaceSpecifiedTileAtRandomPlace(mapValues, replaceTileID, replaceWithTileID);
        Position[] path = Pathfinding.getPathToMainLandWithoutStartEnd(mapValues, start);

        for (int i = 0; i < path.length; i++)
        {
            mapValues[path[i].x][path[i].y] = (short) pathTileID;
        }
        return start;
    }


    /* faster method
    public static void saveGeneratedMapToFile(short[][] mapValues, String filePath)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            for (int y = 0; y < height; y++)
            {
                StringBuilder line = new StringBuilder();
                short currentId = mapValues[0][y];
                int count = 1;

                for (int x = 1; x < width; x++)
                {
                    if (mapValues[x][y] == currentId)
                    {
                        count++;
                    }
                    else
                    {
                        line.append("&").append(currentId).append(" ").append(count).append(" ");
                        currentId = mapValues[x][y];
                        count = 1;
                    }
                }
                line.append("&").append(currentId).append(" ").append(count);   // last sequence in the Y line

                writer.write(line.toString());
                writer.newLine(); // jumping to the new Y line
            }
            System.out.println("Map saved to file");
        }
        catch (IOException e)
        {
            System.err.println("Error saving map to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
     */

    /* old inefficient method
    public static void saveGeneratedMapToFile(short[][] mapValues, String filePath)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            for (int y = 0; y < height; y++)
            {
                StringBuilder line = new StringBuilder();
                for (int x = 0; x < width; x++)
                {
                    line.append(mapValues[x][y]);
                    if (x < width - 1)
                    {
                        line.append(" "); // Separate values with a space
                    }
                }
                writer.write(line.toString());
                writer.newLine(); // New line after each row
            }
            System.out.println("Map saved to file");
        }
        catch (IOException e)
        {
            System.err.println("Error saving map to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
     */
}
