package main.map;

import main.GameController;
import utilities.FileManipulation;
import utilities.Position;

public class Map
{
    private final Chunk[][] chunks;
    //Enum level

    public Map(int mapWidth, int mapHeight, String path)
    {
        int chunkCountX = (int) Math.ceil((double) mapWidth / Chunk.getChunkSize());
        int chunkCountY = (int) Math.ceil((double) mapHeight / Chunk.getChunkSize());
        chunks = new Chunk[chunkCountX][chunkCountY];

        for (int x = 0; x < chunkCountX; x++)
        {
            for (int y = 0; y < chunkCountY; y++)
            {
                chunks[x][y] = new Chunk(new Position(x, y));
            }
        }
        FileManipulation.loadMapFromFile(this, path);
    }

    public Chunk[][] getChunks() {return chunks;}

    public Chunk getChunk(int worldX, int worldY)
    {
        int chunkX = worldX / Chunk.getChunkSize();
        int chunkY = worldY / Chunk.getChunkSize();
        return chunks[chunkX][chunkY];
    }



}
