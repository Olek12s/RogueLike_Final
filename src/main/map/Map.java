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
    private String filePath;

    public int getChunkCountX() {return chunkCountX;}
    public int getChunkCountY() {return chunkCountY;}

    //Enum level

    public Map(int mapWidth, int mapHeight, String path)
    {
        this.chunkCountX = mapWidth;
        this.chunkCountY = mapHeight;
        this.filePath = path;
        System.out.println(chunkCountX);
        System.out.println(chunkCountY);
        chunks = new Chunk[chunkCountX][chunkCountY];
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        createChunks();         //Fills map with empty new Chunk objects
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
        int chunkSize = Chunk.getChunkSize();
        int chunkPixelSize = Chunk.getChunkSize() * Tile.tileSize;
        int halfMapWidthInPixels = (chunkCountX / 2) * chunkPixelSize;
        int halfMapHeightInPixels = (chunkCountY / 2) * chunkPixelSize;

        for (int x = 0; x < chunkCountX; x++)
        {
            for (int y = 0; y < chunkCountY; y++)
            {
                int worldX = x * chunkPixelSize - halfMapWidthInPixels;
                int worldY = y * chunkPixelSize - halfMapHeightInPixels;


                //Tile[][] chunkTiles = createDefaultChunkTiles();    // load chunk from map file in here
                Tile[][] chunkTiles = loadChunkTilesFromFile(filePath, x*chunkSize, y*chunkSize);

                Position chunkPosition = new Position(worldX, worldY);
                chunks[x][y] = new Chunk(chunkPosition, chunkTiles);

                System.out.println("Chunk" + "(" + x + ", " + y + ") created at: " + chunkPosition);   // TEMP DEBUG
            }
        }
    }

    private Tile[][] createDefaultChunkTiles()
    {
        Tile[][] tiles = new Tile[Chunk.getChunkSize()][Chunk.getChunkSize()];
        for (int x = 0; x < Chunk.getChunkSize(); x++)
        {
            for (int y = 0; y < Chunk.getChunkSize(); y++)
            {
                tiles[x][y] = Tile.defaultTileObject;   // static object
            }
        }
        return tiles;
    }


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

                        chunkTiles[x][y] = new Tile(id);    // <--- Add TileManager and pass references to the tiles in here to prevent memory leaks
                        //chunkTiles[x][y] = Tile.defaultTileObject;
                    }
                    catch(Exception ex)
                    {
                        chunkTiles[x][y] = new Tile();     // <--- Add TileManager and pass references to the tiles in here to prevent memory leaks
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

}
