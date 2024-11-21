package main.map;

import main.DrawPriorities;
import main.Drawable;
import utilities.Position;
import utilities.camera.Camera;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MapRenderer implements Drawable
{
    MapController mapController;
    public static int chunkRenderDistance = 3;

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

        Position cameraPosition = mapController.gc.camera.getCameraPosition();
        Chunk cameraChunk = mapController.getCurrentMap().getChunk(cameraPosition);


        int cameraChunkX = cameraChunk.getxIndex();
        int cameraChunkY = cameraChunk.getyIndex();
        int startChunkX = max(0, cameraChunkX - chunkRenderDistance);
        int startChunkY = max(0, cameraChunkY - chunkRenderDistance);
        int endChunkX = min(mapController.getCurrentMap().getChunkCountX(), cameraChunkX + chunkRenderDistance + 1);
        int endChunkY = min(mapController.getCurrentMap().getChunkCountY(), cameraChunkY + chunkRenderDistance + 1);




        // for each chunk in range of the camera
        for (int chunkX = startChunkX; chunkX < endChunkX; chunkX++)
        {
            for (int chunkY = startChunkY; chunkY < endChunkY; chunkY++)
            {
                Chunk chunk = mapController.getCurrentMap().getChunkByIndex(chunkX, chunkY);

                // for each tile from the chunk
                for (int tileX = 0; tileX < chunkSize; tileX++)
                {
                    for (int tileY = 0; tileY < chunkSize; tileY++)
                    {
                        Tile tile = chunk.getTiles()[tileX][tileY];
                        int worldX = chunk.getChunkWorldPosition().x + (tileX * tileSize);
                        int worldY = chunk.getChunkWorldPosition().y + (tileY * tileSize);
                        Position screenPosition = mapController.gc.camera.applyCameraOffset(worldX, worldY);

                        int scaleFactor = (int) Math.ceil(Camera.getScaleFactor() * tileSize);
                        g2.drawImage(tile.getCurrentSprite().image, screenPosition.x, screenPosition.y, scaleFactor, scaleFactor, null);
                    }
                }
            }
        }
    }
}
