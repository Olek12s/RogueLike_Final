package main.tile;

import main.Drawable;
import main.Updatable;
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
    public void draw(Graphics g2) {

    }

    @Override
    public void update()
    {

    }
}
