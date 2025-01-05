package utilities;

import world.map.tiles.TileManager;

import java.util.LinkedList;
import java.util.Queue;

public class Pathfinding
{
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
}
