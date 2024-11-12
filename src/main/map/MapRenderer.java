package main.map;

import main.DrawPriorities;
import main.Drawable;

import java.awt.*;

public class MapRenderer implements Drawable
{

    @Override
    public int getDrawPriority() {return DrawPriorities.mapGrid.value;}

    @Override
    public void draw(Graphics g2)   // drawing chunks (tiles)
    {
        g2.drawRect(50,50,50,50);
    }
}
