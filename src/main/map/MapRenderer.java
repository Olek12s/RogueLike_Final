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

    }
}
