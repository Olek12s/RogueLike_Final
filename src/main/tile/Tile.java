package main.tile;

import main.Drawable;
import main.Updatable;
import utilities.DrawPriorities;
import utilities.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile implements Drawable, Updatable
{
    private Sprite sprite;
    private final int id;
    public boolean collision = false;

    public Tile(Sprite sprite, int id)
    {
        this.sprite = sprite;
        this.id = id;
    }

    public int getId() {return id;}
    public Sprite getSprite() {return sprite;}

    @Override
    public int getDrawPriority() {return DrawPriorities.mapGrid.value;}

    @Override
    public void draw(Graphics g2) {
        System.out.println("test");
    }

    @Override
    public void update()
    {

    }
}
