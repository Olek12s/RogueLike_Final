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
        g2.drawRect(screenPosition.x, screenPosition.y, 50,50);


    }
}
