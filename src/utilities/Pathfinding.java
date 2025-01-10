package utilities;

import world.map.tiles.TileManager;

import java.util.*;

public class Pathfinding
{
    /**
     *  Finds nearest Tile with collisions set to false using BFS algorithm
     *
     * @param mapValues map to search
     * @param start position from which searching begins
     * @return
     */
    public static Position findNearestNonCollidableTile(short[][] mapValues, Position start)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;

        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[height][width];
        queue.add(start);
        visited[start.y][start.x] = true;


        // Directions that pathfinding is allowed to move: top, bottom, left, right
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        while (!queue.isEmpty())
        {
            Position currentPosition = queue.poll();

            for (int i = 0; i < 4; i++)
            {
                int nx = currentPosition.x + dx[i];
                int ny = currentPosition.y + dy[i];

                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[ny][nx])    // checking boundaries
                {
                    visited[ny][nx] = true;

                    // found non collidable Tile
                    if (!TileManager.getTileObject(mapValues[ny][nx]).isColliding())
                    {
                        return new Position(nx, ny);
                    }
                    queue.add(new Position(nx, ny));
                }
            }
        }
        return null;    // case if there's no non-collidable Tile in the whole map
    }


    /**
     *  returns array containing shortest path, which begins from start.
     *
     * @param mapValues map to search
     * @param start position from which searching begins
     * @return returns Position[] array containing shortest path
     */
    public static Position[] getPathToClosestNonCollidableTile(short[][] mapValues, Position start)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;

        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[width][height];
        Position[][] previous = new Position[width][height];

        queue.add(start);
        visited[start.x][start.y] = true;
        previous[start.x][start.y] = null;

        // Directions for pathfinding: up, down, left, right
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        while (!queue.isEmpty())
        {
            Position currentPosition = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = currentPosition.x + dx[i];
                int ny = currentPosition.y + dy[i];

                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[nx][ny])
                {
                    visited[nx][ny] = true;
                    previous[nx][ny] = currentPosition;

                    if (!TileManager.getTileObject(mapValues[nx][ny]).isColliding())    // found a non-collidable tile
                    {
                        return buildPath(previous, start, new Position(nx, ny));
                    }
                    queue.add(new Position(nx, ny));
                }
            }
        }
        return null; // No non-collidable tile found
    }

    /**
     * Builds a path from the start position to the end position based on the `previous` positions array.
     *
     * @param previous
     * @param start The starting position of the path.
     * @param end The ending position of the path.
     * @return An array of `Position` objects representing the path from `start` to `end`.
     *  *                 If no path exists, the array will only contain `end` or may be empty depending
     *  *                 on the input state.
     */
    private static Position[] buildPath(Position[][] previous, Position start, Position end)
    {

        List<Position> path = new ArrayList<>();
        Position current = end;

        while (current != null)
        {
            path.add(current);
            current = previous[current.x][current.y];
        }

        Collections.reverse(path);
        return path.toArray(new Position[0]);
    }

    /**
     *  returns array containing shortest path, which begins from start. Excludes start point and
     *  end point
     * @param mapValues map to search
     * @param start position from which searching begins
     * @return returns Position[] array containing shortest path
     */
    public static Position[] getPathToClosestNonCollidableTileWithoutStartEnd(short[][] mapValues, Position start)
    {
        Position[] fullPath = getPathToClosestNonCollidableTile(mapValues, start);
        if (fullPath == null || fullPath.length <= 2)
        {
            //System.err.println("There was trial to trim array of size 2 or null.");
            return new Position[0];
        }
        return Arrays.copyOfRange(fullPath, 1, fullPath.length - 1);
    }



    /**
     *
     *
     * @param mapValues
     * @param x
     * @param y
     * @return
     */
    private static int countNonCollidableNeighbors(short[][] mapValues, int x, int y) {
        int width = mapValues.length;
        int height = mapValues[0].length;

        // Directions: right, down, left, up
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        int count = 0;

        for (int i = 0; i < 4; i++)
        {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < width && ny >= 0 && ny < height)
            {
                if (!TileManager.getTileObject(mapValues[ny][nx]).isColliding())
                {
                    count++;
                }
            }
        }
        return count;
    }

    public static Position[] getPathToMainLandWithoutStartEnd(short[][] mapValues, Position start)
    {
        Position[] path = getPathToMainLand(mapValues, start);
        if (path == null || path.length <= 2)
        {
            //System.err.println("There was trial to trim array of size 2 or null.");
            return new Position[0];
        }
        return Arrays.copyOfRange(path, 1, path.length - 1);

    }

    public static Position[] getPathToMainLand(short[][] mapValues, Position start, int landSize)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;

        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[width][height];
        Position[][] previous = new Position[width][height];

        visited[start.x][start.y] = true;
        previous[start.x][start.y] = null;
        queue.add(start);

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        while (!queue.isEmpty())
        {
            Position current = queue.poll();

            if (!TileManager.getTileObject(mapValues[current.x][current.y]).isColliding())
            {
                int regionSize = getRegionSize(mapValues, current);
                if (regionSize >= landSize)
                {
                    return buildPath(previous, start, current);
                }
            }

            for (int i = 0; i < 4; i++)
            {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[nx][ny])
                {
                    visited[nx][ny] = true;
                    previous[nx][ny] = current;
                    queue.add(new Position(nx, ny));
                }
            }
        }
        return null;
    }

    public static Position[] getPathToMainLand(short[][] mapValues, Position start)
    {
        return getPathToMainLand(mapValues, start, 100);
    }

    private static int getRegionSize(short[][] mapValues, Position start)
    {
        int width = mapValues.length;
        int height = mapValues[0].length;
        final int maxSize = 1000;

        boolean[][] visited = new boolean[width][height];
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        visited[start.x][start.y] = true;

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        int size = 0;

        while (!queue.isEmpty())
        {
            Position current = queue.poll();
            size++;

            for (int i = 0; i < 4; i++)
            {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[nx][ny])
                {
                    if (!TileManager.getTileObject(mapValues[nx][ny]).isColliding())
                    {
                        visited[nx][ny] = true;
                        queue.add(new Position(nx, ny));
                    }
                }
            }
            if (size >= maxSize) break; // do not count whole map.
        }
        return size;
    }

}
