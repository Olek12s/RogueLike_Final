package main.tile;

import main.Drawable;
import main.Updatable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile implements Drawable, Updatable
{
    private BufferedImage texture;
    private final int id;

    public Tile(BufferedImage texture, int id)
    {
        this.texture = texture;
        this.id = id;
    }

    public int getId() {return id;}

    @Override
    public void draw(Graphics g2) {

    }

    @Override
    public void update() {

    }
}
