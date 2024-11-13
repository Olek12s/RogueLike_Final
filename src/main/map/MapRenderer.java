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

        int worldX = 0;
        int worldY = 0;
        Position screenPosition = mapController.gc.camera.applyCameraOffset(worldX, worldY);

        for (int i = 0; i < mapController.getCurrentMap().getChunkCountX(); i++)
        {
            for (int j = 0; j < mapController.getCurrentMap().getChunkCountY(); i++)
            {
                for (int k = 0; k < chunks[1].length; k++)
                {
                    for (int l = 0; l < chunks.length; l++)
                    {
                        g2.drawImage(chunks[i][j].getTiles()[k][l].getCurrentSprite().image, worldX, worldY, null);
                    }
                }
            }
        }
        g2.drawRect(screenPosition.x, screenPosition.y, 50,50);
    }

    private void drawChunk(int worldX, int worldY, Graphics g2)
    {

    }
}
