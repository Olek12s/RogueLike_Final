package world.map;

import main.entity.Entity;
import utilities.Hitbox;
import utilities.Position;
import world.map.tiles.Tile;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Map
{
    private final Chunk[][] chunks;
    private int chunkCountX;
    private int chunkCountY;
    private String filePath;

    public int getChunkCountX() {return chunkCountX;}
    public int getChunkCountY() {return chunkCountY;}

    public int getMapWidthInPixels() {return chunkCountX * Chunk.getChunkSize() * Tile.tileSize;}
    public int getMapHeightInPixels() {return chunkCountY * Chunk.getChunkSize() * Tile.tileSize;}

    //Enum level

    public Map(int mapWidth, int mapHeight, String path)
    {
        this.chunkCountX = mapWidth;
        this.chunkCountY = mapHeight;
        this.filePath = path;
        chunks = new Chunk[chunkCountX][chunkCountY];

        loadMapFromFile();         //Fills map with new Chunk objects
    }

    public Chunk[][] getChunks() {return chunks;}

    public Chunk getChunk(int worldX, int worldY)
    {
        int chunkPixelSize = Chunk.getChunkSize() * Tile.tileSize;

        // aligning to the middle of the map
        int halfMapWidthInPixels = (chunkCountX / 2) * chunkPixelSize;
        int halfMapHeightInPixels = (chunkCountY / 2) * chunkPixelSize;
        int adjustedX = worldX + halfMapWidthInPixels;
        int adjustedY = worldY + halfMapHeightInPixels;

        int chunkX = adjustedX / chunkPixelSize;
        int chunkY = adjustedY / chunkPixelSize;

        if (adjustedX < 0) chunkX--;
        if (adjustedY < 0) chunkY--;

        if (chunkX < 0 || chunkX >= chunkCountX || chunkY < 0 || chunkY >= chunkCountY) {

            throw new IndexOutOfBoundsException("Position out of map: (" + worldX + ", " + worldY + ")");
        }
        return chunks[chunkX][chunkY];
    }
    public Chunk getChunk(Position worldPosition) {return getChunk(worldPosition.x, worldPosition.y);}
    public Chunk getChunkByIndex(int chunkX, int chunkY) {return chunks[chunkX][chunkY];}

    public Tile getTile(int worldX, int worldY)
    {
        Chunk chunk = getChunk(worldX, worldY);
        int chunkPixelSize = Chunk.getChunkSize() * Tile.tileSize;

        // aligning to the middle of the map
        int halfMapWidthInPixels = (chunkCountX / 2) * chunkPixelSize;
        int halfMapHeightInPixels = (chunkCountY / 2) * chunkPixelSize;
        int adjustedX = worldX + halfMapWidthInPixels;
        int adjustedY = worldY + halfMapHeightInPixels;

        int tileXInChunk = (adjustedX % chunkPixelSize) / Tile.tileSize;
        int tileYInChunk = (adjustedY % chunkPixelSize) / Tile.tileSize;

        if (tileXInChunk < 0) tileXInChunk += Chunk.getChunkSize();
        if (tileYInChunk < 0) tileYInChunk += Chunk.getChunkSize();

        Tile tile = chunk.getTiles()[tileXInChunk][tileYInChunk];
        return tile;
    }
    public Tile getTile(Position position) {return getTile(position.x, position.y);}

    private void loadMapFromFile()
    {
        int chunkSize = Chunk.getChunkSize();
        Tile[][] defaultChunkTiles = createDefaultChunkTiles(); // initializing default chunks
        short[][] mapValues = null;

        // Loading serialized file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            mapValues = (short[][]) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Unable to load map file. Using default chunks.");
        }

        // Creating chunks
        long start = System.nanoTime();
        for (int x = 0; x < chunkCountX; x++)
        {
            for (int y = 0; y < chunkCountY; y++)
            {
                Tile[][] chunkTiles;

                if (mapValues != null)    // divide loaded map into 8x8 chunks
                {
                    chunkTiles = extractChunkTilesFromMapArray(mapValues, x * chunkSize, y * chunkSize);
                }
                else                     // else use default chunks
                {
                    chunkTiles = defaultChunkTiles;
                }

                // creating and positioning chunk
                int worldX = x * chunkSize * Tile.tileSize - (chunkCountX / 2) * chunkSize * Tile.tileSize;
                int worldY = y * chunkSize * Tile.tileSize - (chunkCountY / 2) * chunkSize * Tile.tileSize;
                Position chunkPosition = new Position(worldX, worldY);
                chunks[x][y] = new Chunk(chunkPosition, chunkTiles, x, y);
            }
        }
        long end = System.nanoTime();
        System.out.println("All chunks created in: " + (float) (end - start) / 1_000_000_000 + " seconds.");
    }

    private Tile[][] extractChunkTilesFromMapArray(short[][] mapValues, int startX, int startY)
    {
        int chunkSize = Chunk.getChunkSize();
        int mapWidthInTiles = mapValues.length;
        int mapHeightInTiles = mapValues[0].length;
        int halfMapWidthInPixels = (mapWidthInTiles * Tile.tileSize) / 2;
        int halfMapHeightInPixels = (mapHeightInTiles * Tile.tileSize) / 2;

        Tile[][] chunkTiles = new Tile[chunkSize][chunkSize];
        int defaultTilesCounter = 0;

        for (int y = 0; y < chunkSize; y++)
        {
            for (int x = 0; x < chunkSize; x++)
            {
                int mapX = startX + x;
                int mapY = startY + y;

                int worldX = (mapX * Tile.tileSize) - halfMapWidthInPixels;
                int worldY = (mapY * Tile.tileSize) - halfMapHeightInPixels;

                if (mapX < mapValues.length && mapY < mapValues[0].length)
                {
                    short id = mapValues[mapX][mapY];
                    try
                    {
                        chunkTiles[x][y] = new Tile(id, new Position(worldX, worldY));
                    }
                    catch (Exception ex)
                    {
                        chunkTiles[x][y] = new Tile(new Position(worldX, worldY));
                        defaultTilesCounter++;
                    }
                }
                else
                {
                    chunkTiles[x][y] = new Tile(new Position(worldX, worldY));
                    defaultTilesCounter++;
                }
            }
        }
        if (defaultTilesCounter > 0)
        {
            System.err.println("Default tiles used: " + defaultTilesCounter);
        }
        return chunkTiles;
    }

    private Tile[][] createDefaultChunkTiles()
    {
        Tile[][] tiles = new Tile[Chunk.getChunkSize()][Chunk.getChunkSize()];
        for (int x = 0; x < Chunk.getChunkSize(); x++)
        {
            for (int y = 0; y < Chunk.getChunkSize(); y++)
            {
                tiles[x][y] = new Tile(new Position(0, 0));
            }
        }
        return tiles;
    }

    public ArrayList<Chunk> getChunkNeighborsNotDiagonals(Chunk sourceChunk)
    {
        ArrayList<Chunk> resultChunks = new ArrayList<>();
        int sourceChunkXIndex = sourceChunk.getxIndex();
        int sourceChunkYIndex = sourceChunk.getyIndex();

        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                if ((i == 0 && j == 0) || (i != 0 && j != 0)) continue; // ignore source chunks and diagonlas

                int neighborX = sourceChunkXIndex + i;
                int neighborY = sourceChunkYIndex + j;

                if (neighborX >= 0 && neighborY >= 0 && neighborX < chunks.length && neighborY < chunks[0].length)
                {
                    Chunk neighbor = chunks[neighborX][neighborY];
                    if (neighbor != null)
                    {
                        resultChunks.add(neighbor);
                    }
                }

            }
        }
        return resultChunks;
    }

    public ArrayList<Chunk> getChunkNeighborsDiagonals(Chunk sourceChunk)
    {
        ArrayList<Chunk> resultChunks = new ArrayList<>();
        int sourceChunkXIndex = sourceChunk.getxIndex();
        int sourceChunkYIndex = sourceChunk.getyIndex();

        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                if (i == 0 && j == 0) continue; // Ignore the source chunk

                int neighborX = sourceChunkXIndex + i;
                int neighborY = sourceChunkYIndex + j;

                // Check if the neighbor is within map bounds
                if (neighborX >= 0 && neighborY >= 0 && neighborX < chunks.length && neighborY < chunks[0].length)
                {
                    Chunk neighbor = chunks[neighborX][neighborY];
                    if (neighbor != null)
                    {
                        resultChunks.add(neighbor);
                    }
                }
            }
        }
        return resultChunks;
    }

    /**
     * seeks for the nearest non-collidable position, where hitbox can fit.
     *
     * @param position The position to check.
     * @param hitbox   The hitbox to validate.
     * @return true if the position is valid for the hitbox, false otherwise.
     */
    public Position seekForNearestNonCollidableSpawnPosition(Position position, Hitbox hitbox)
    {
        Position[] directions =
                {
                new Position(-1, 0),  // left
                new Position(0, -1),  // up
                new Position(1, 0),   // right
                new Position(0, 1),   // down
                new Position(-1, -1), // top-left diagonal
                new Position(1, -1),  // top-right diagonal
                new Position(-1, 1),  // bottom-left diagonal
                new Position(1, 1)    // bottom-right diagonal
        };

        // Check if the initial position is valid
        if (isPositionValidForFittingEntity(position, hitbox))
        {
            return position;
        }

        int searchRadius = 1;

        while (true)    // loop without limitations might lead to unpredicted crash. It is suggested to set limit
        {
            for (int d = 0; d < directions.length; d++)
            {
                for (int step = 0; step < searchRadius; step++)
                {
                    Position candidate = new Position(
                            position.x + directions[d].x * searchRadius,
                            position.y + directions[d].y * searchRadius
                    );

                    if (isPositionValidForFittingEntity(candidate, hitbox)) // if candidate is valid, return it
                    {
                        return candidate;
                    }
                    else
                    {
                        // not fitting
                    }
                }
            }
            searchRadius++;
        }
    }

    /**
     * Checks if the given position can fit the hitbox without colliding with any obstacles such as collidable tiles or other hitboxes
     * Method is guaranteed to find the best solution
     *
     * @param position The position to check.
     * @param entityHitbox   The entity's hitbox to validate.
     * @return true if the position is valid for the hitbox, false otherwise.
     */
    private boolean isPositionValidForFittingEntity(Position position, Hitbox entityHitbox)
    {
        // Calculate the corners of the hitbox relative to the given position
        Position leftUp = new Position(position.x, position.y);
        Position rightUp = new Position(position.x + entityHitbox.getWidth(), position.y);
        Position leftDown = new Position(position.x, position.y + entityHitbox.getHeight());
        Position rightDown = new Position(position.x + entityHitbox.getWidth(), position.y + entityHitbox.getHeight());
        Position middle = new Position(position.x + entityHitbox.getWidth() / 2, position.y + entityHitbox.getHeight() / 2);

        // Check if any part of the hitbox collides with the collidable
        if (getTile(leftUp).isColliding() ||
                getTile(rightUp).isColliding() ||
                getTile(leftDown).isColliding() ||
                getTile(rightDown).isColliding() ||
                getTile(middle).isColliding())
        {
            return false;
        }

        // get entities from current chunk and neighbours
        Chunk currentChunk = getChunk(position);
        ArrayList<Chunk> neighboringChunks = getChunkNeighborsDiagonals(currentChunk);
        neighboringChunks.add(currentChunk);

        Rectangle hitboxRect = entityHitbox.getHitboxRect();
        hitboxRect.setLocation(position.x, position.y);

        for (Chunk chunk : neighboringChunks)
        {
            for (Entity entity : chunk.getEntities())
            {
                Hitbox otherHitbox = entity.getHitbox();
                Rectangle otherRect = otherHitbox.getHitboxRect();
                otherRect.setLocation(entity.getWorldPosition().x, entity.getWorldPosition().y);

                if (hitboxRect.intersects(otherRect))
                {
                    return false;
                }
            }
        }
        return true; // valid
    }

    /* Faster method, using 1*x Rectangles algorithm to shorten map file

    public Tile[][] loadChunkTilesFromFile(String path, int startX, int startY) {
        int chunkSize = Chunk.getChunkSize();
        Tile[][] chunkTiles = new Tile[chunkSize][chunkSize];

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int defaultTilesCounter = 0;

            // skipping to the startY
            for (int i = 0; i < startY; i++)
            {
                br.readLine();
            }

            for (int y = 0; y < chunkSize; y++)
            {
                int x = 0;

                String line = br.readLine();
                if (line == null)
                {
                    throw new IOException("Unexpected end of file while reading chunk.");
                }

                String[] tokens = line.trim().split("&");
                int tilesToSkip = startX;

                for (String token : tokens)
                {
                    if (!token.isEmpty())
                    {
                        String[] parts = token.trim().split(" ");
                        if (parts.length != 2) {
                            throw new IOException("Invalid token format: " + token);
                        }

                        short id = Short.parseShort(parts[0]);
                        int count = Integer.parseInt(parts[1]);

                        // skipping
                        if (tilesToSkip > 0)
                        {
                            if (tilesToSkip >= count)
                            {
                                tilesToSkip -= count; // skip whole token
                                continue;
                            } else {
                                count -= tilesToSkip; //skip part of the token
                                tilesToSkip = 0;
                            }
                        }

                        // inserting tiles to the array
                        while (count > 0 && x < chunkSize)
                        {
                            try
                            {
                                chunkTiles[x][y] = new Tile(id);
                            } catch (Exception ex) {
                                chunkTiles[x][y] = TileManager.defaultTileObject;
                                defaultTilesCounter++;
                            }
                            count--;
                            x++;
                        }

                        // if chunk is filled
                        if (x >= chunkSize) {
                            break;
                        }
                    }
                }
                if (x != chunkSize)
                {
                    throw new IOException("Row does not match expected chunk size.");
                }
            }

            if (defaultTilesCounter != 0) {
                System.err.println("Created " + defaultTilesCounter + " default tiles for chunk (" +
                        startX / chunkSize + ", " + startY / chunkSize + ")");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return chunkTiles;
    }
    */

    /* old inefficient method
    public Tile[][] loadChunkTilesFromFile(String path, int startX, int startY)
    {
        int chunkSize = Chunk.getChunkSize();

        Tile[][] chunkTiles = new Tile[chunkSize][chunkSize];

        try
        {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            int defaultTilesCounter = 0;

            for (int i = 0; i < startY; i++)    // skip first Y lines
            {
                br.readLine();
            }

            for (int y = 0; y < chunkSize; y++)
            {
                String line = br.readLine();

                for (int x = 0; x < chunkSize; x++)
                {
                    try
                    {
                        String[] lineTiles = line.trim().split(" ");
                        int tileX = startX + x;
                        int id = Integer.parseInt(lineTiles[tileX]);
                        chunkTiles[x][y] = new Tile(id);
                    }
                    catch(Exception ex)
                    {
                        chunkTiles[x][y] = TileManager.defaultTileObject;     // <--- TileManager to pass references to the tiles in here to prevent memory leaks
                        defaultTilesCounter++;
                    }
                }
            }
            if (defaultTilesCounter != 0) System.err.println("Created " + defaultTilesCounter + " default tiles for chunk (" + startX / chunkSize + ", " + startY / chunkSize + ")");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return chunkTiles;
    }
     */
}
