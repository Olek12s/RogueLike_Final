package world.map;

import main.DrawPriorities;
import main.Drawable;
import utilities.Position;
import utilities.camera.Camera;
import world.map.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MapRenderer implements Drawable
{
    MapController mapController;
    public static int chunkRenderDistance = 4;  // default value. Value is being updated in camera Updater

    public MapRenderer(MapController mapController)
    {
        this.mapController = mapController;
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.mapGrid.value;}

    @Override
    public void draw(Graphics g2)   // drawing chunks (tiles)
    {
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

                        if (isTileVisible(worldX, worldY, tileSize))
                        {
                            Position screenPosition = mapController.gc.camera.applyCameraOffset(worldX, worldY);
                            int scaleFactor = (int) Math.ceil(Camera.getScaleFactor() * tileSize);
                            g2.drawImage(tile.getCurrentSprite().image, screenPosition.x, screenPosition.y, scaleFactor, scaleFactor, null);
                            mapController.gc.incrementRenderCount();
                        }
                    }
                }
                if (mapController.gc.isDebugMode()) drawChunkBoundary(g2, chunk);
            }
        }
    }

    private void drawChunkBoundary(Graphics g2, Chunk chunk)
    {
        int chunkPixelSize = Chunk.getChunkSize() * Tile.tileSize;
        final int chunkBoundaryThickness = 1;

        Position chunkWorldPos = chunk.getChunkWorldPosition();
        Position screenPos = mapController.gc.camera.applyCameraOffset(chunkWorldPos.x, chunkWorldPos.y);

        double scaleFactor = Camera.getScaleFactor();
        int scaledWidth  = (int) (chunkPixelSize * scaleFactor);
        int scaledHeight = (int) (chunkPixelSize * scaleFactor);
        float scaledThickness = (float)(chunkBoundaryThickness * scaleFactor);

        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(scaledThickness));
        g2d.drawRect(screenPos.x, screenPos.y, scaledWidth, scaledHeight);
        mapController.gc.incrementRenderCount();
    }

    private boolean isTileVisible(int worldX, int worldY, int tileSize)
    {
        Position cameraPosition = mapController.gc.camera.getCameraPosition();
        double scaleFactor = Camera.getScaleFactor();

        int screenWidth = mapController.gc.getWidth();
        int screenHeight = mapController.gc.getHeight();

        int cameraLeft = (int) (cameraPosition.x - (screenWidth / 2) / scaleFactor);
        int cameraRight = (int) (cameraPosition.x + (screenWidth / 2) / scaleFactor);
        int cameraTop = (int) (cameraPosition.y - (screenHeight / 2) / scaleFactor);
        int cameraBottom = (int) (cameraPosition.y + (screenHeight / 2) / scaleFactor);

        int tileRight = worldX + tileSize;
        int tileBottom = worldY + tileSize;

        return tileRight > cameraLeft && worldX < cameraRight && tileBottom > cameraTop && worldY < cameraBottom;
    }
}
