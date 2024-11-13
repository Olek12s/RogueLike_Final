package main.map;

import main.GameController;
import utilities.FileManipulation;
import utilities.Position;

import java.io.*;

public class Map
{
    private final Chunk[][] chunks;
    private int chunkCountX;
    private int chunkCountY;
    private int mapWidth;
    private int mapHeight;

    public int getChunkCountX() {return chunkCountX;}
    public int getChunkCountY() {return chunkCountY;}

    //Enum level

    public Map(int mapWidth, int mapHeight, String path)
    {
        this.chunkCountX = (int) Math.ceil((double) mapWidth / Chunk.getChunkSize());
        this.chunkCountY = (int) Math.ceil((double) mapHeight / Chunk.getChunkSize());
        chunks = new Chunk[chunkCountX][chunkCountY];
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        createChunks();         //Fills map with empty new Chunk objects
        loadChunksFromFile(path);
    }

    public Chunk[][] getChunks() {return chunks;}

    public Chunk getChunk(int worldX, int worldY)
    {
        int chunkX = worldX / Chunk.getChunkSize();
        int chunkY = worldY / Chunk.getChunkSize();
        return chunks[chunkX][chunkY];
    }

    private void createChunks()
    {
        int chunkPixelSize = Chunk.getChunkSize() * Tile.tileSize;
        int halfMapWidthInPixels = (chunkCountX / 2) * chunkPixelSize;
        int halfMapHeightInPixels = (chunkCountY / 2) * chunkPixelSize;

        for (int x = 0; x < chunkCountX; x++)
        {
            for (int y = 0; y < chunkCountY; y++)
            {
                int worldX = x * chunkPixelSize - halfMapWidthInPixels;
                int worldY = y * chunkPixelSize - halfMapHeightInPixels;
                chunks[x][y] = new Chunk(new Position(worldX, worldY));
            }
        }
    }


    /**
     * Loads map from .txt format. Fills current map with freshly made 2D chunk objects matrix.
     * Chunk objects are filled within same function with new 2D tile objects matrix.
     *
     */
    public void loadChunksFromFile(String path)
    {
        try
        {
            InputStream is = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // gets chunk from collection, and fills it from file with new tiles
            for (int x = 0; x < chunkCountX; x++)
            {
                for (int y = 0; y < chunkCountY; y++)
                {
                    int chunkWorldPositionX = x * Chunk.getChunkSize() * Tile.tileSize;
                    int chunkWorldPositionY = y * Chunk.getChunkSize() * Tile.tileSize;
                    chunks[x][y] = new Chunk(new Position(chunkWorldPositionX, chunkWorldPositionY));
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
