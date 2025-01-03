package world.map;

import utilities.Position;
import world.map.tiles.Tile;
import world.map.tiles.TileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Map
{
    private final Chunk[][] chunks;
    private int chunkCountX;
    private int chunkCountY;
    private String filePath;

    public int getChunkCountX() {return chunkCountX;}
    public int getChunkCountY() {return chunkCountY;}

    //Enum level

    public Map(int mapWidth, int mapHeight, String path)
    {
        this.chunkCountX = mapWidth;
        this.chunkCountY = mapHeight;
        this.filePath = path;
        chunks = new Chunk[chunkCountX][chunkCountY];

        createChunks();         //Fills map with empty new Chunk objects
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

    private void createChunks()
    {
        int chunkSize = Chunk.getChunkSize();
        int chunkPixelSize = Chunk.getChunkSize() * Tile.tileSize;
        int halfMapWidthInPixels = (chunkCountX / 2) * chunkPixelSize;
        int halfMapHeightInPixels = (chunkCountY / 2) * chunkPixelSize;

        long start = System.nanoTime();
        for (int x = 0; x < chunkCountX; x++)
        {
            for (int y = 0; y < chunkCountY; y++)
            {
                int worldX = x * chunkPixelSize - halfMapWidthInPixels;
                int worldY = y * chunkPixelSize - halfMapHeightInPixels;


                //Tile[][] chunkTiles = createDefaultChunkTiles();    // load chunk from map file in here
                Tile[][] chunkTiles = loadChunkTilesFromFile(filePath, x*chunkSize, y*chunkSize);

                Position chunkPosition = new Position(worldX, worldY);
                chunks[x][y] = new Chunk(chunkPosition, chunkTiles, x, y);
            }
            System.out.println("Chunk loaded from file");
        }
        long end = System.nanoTime();
        long elapsed = end-start;
        System.out.println("Elapsed: " + elapsed/1000000000 + " seconds");
        if (elapsed >= 3) System.err.println("PRZEKROCZONY CZAS");
    }

    private Tile[][] createDefaultChunkTiles()
    {
        Tile[][] tiles = new Tile[Chunk.getChunkSize()][Chunk.getChunkSize()];
        for (int x = 0; x < Chunk.getChunkSize(); x++)
        {
            for (int y = 0; y < Chunk.getChunkSize(); y++)
            {
                tiles[x][y] = TileManager.defaultTileObject;   // static object
            }
        }
        return tiles;
    }



    public Tile[][] loadChunkTilesFromFile(String path, int startX, int startY) {
        int chunkSize = Chunk.getChunkSize();
        Tile[][] chunkTiles = new Tile[chunkSize][chunkSize];

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int defaultTilesCounter = 0;

            // Pomijanie wierszy do startY
            for (int i = 0; i < startY; i++) {
                br.readLine();
            }

            for (int y = 0; y < chunkSize; y++) {
                int x = 0; // Startowa pozycja w wierszu chunku

                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Unexpected end of file while reading chunk.");
                }

                String[] tokens = line.trim().split("&");
                int tilesToSkip = startX; // Liczba kafelków do pominięcia w bieżącym wierszu

                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        // Parsowanie ID i liczby kafelków
                        String[] parts = token.trim().split(" ");
                        if (parts.length != 2) {
                            throw new IOException("Invalid token format: " + token);
                        }

                        short id = Short.parseShort(parts[0]);
                        int count = Integer.parseInt(parts[1]);

                        // Pomijanie kafelków, jeśli trzeba
                        if (tilesToSkip > 0) {
                            if (tilesToSkip >= count) {
                                tilesToSkip -= count; // Pomiń cały token
                                continue;
                            } else {
                                count -= tilesToSkip; // Pomiń część tokena
                                tilesToSkip = 0;
                            }
                        }

                        // Wstawianie kafelków do chunkTiles
                        while (count > 0 && x < chunkSize) {
                            try {
                                chunkTiles[x][y] = new Tile(id);
                            } catch (Exception ex) {
                                chunkTiles[x][y] = TileManager.defaultTileObject;
                                defaultTilesCounter++;
                            }
                            count--;
                            x++;
                        }

                        // Jeśli wiersz chunku jest wypełniony, przerwij
                        if (x >= chunkSize) {
                            break;
                        }
                    }
                }

                // Weryfikacja, czy wiersz chunku jest pełny
                if (x != chunkSize) {
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
}
