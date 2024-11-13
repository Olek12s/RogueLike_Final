package main.map;

import main.DrawPriorities;
import main.Drawable;
import utilities.Position;

import java.awt.*;

public class MapRenderer implements Drawable
{
    MapController mapController;

    public MapRenderer(MapController mapController)
    {
        this.mapController = mapController;
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.mapGrid.value;}

    @Override
    public void draw(Graphics g2)   // drawing chunks (tiles)
    {
        Chunk[][] chunks = mapController.getCurrentMap().getChunks();
        int chunkSize = Chunk.getChunkSize();
        int tileSize = Tile.tileSize;

        // for each chunk on the map
        for (int chunkX = 0; chunkX < mapController.getCurrentMap().getChunkCountX(); chunkX++)
        {
            for (int chunkY = 0; chunkY < mapController.getCurrentMap().getChunkCountY(); chunkY++)
            {
                Chunk chunk = chunks[chunkX][chunkY];
                Position chunkWorldPosition = chunk.getChunkWorldPosition();

                // for each tile from the chunk
                for (int tileX = 0; tileX < chunkSize; tileX++)
                {
                    for (int tileY = 0; tileY < chunkSize; tileY++)
                    {
                        Tile tile = chunk.getTiles()[tileX][tileY];


                        int worldX = chunkWorldPosition.x + (tileX * tileSize);
                        int worldY = chunkWorldPosition.y + (tileY * tileSize);
                        Position screenPosition = mapController.gc.camera.applyCameraOffset(worldX, worldY);


                        g2.drawImage(tile.getCurrentSprite().image, screenPosition.x, screenPosition.y, tileSize, tileSize, null);
                    }
                }
            }
        }
    }
}
