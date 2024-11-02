package main.tile;

import main.Drawable;
import main.GameController;
import utilities.DrawPriorities;
import utilities.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static utilities.ImageManipulation.loadImage;

public class TileManager implements Drawable
{
    GameController gc;

    public final int tileSize = 64;
    private Map<Integer, Tile> tiles;

    public TileManager(GameController gc)
    {
        this.gc = gc;
        tiles = new HashMap<>();
        loadTiles();
    }

    private void loadTiles()
    {
        int i = 0;
        tiles.put(i, new Tile(new Sprite(loadImage("resources/default/defaultTile"), tileSize), i++));
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Tile.value;}

    @Override
    public void draw(Graphics g2)
    {
        g2.drawImage(tiles.get(0).getSprite().image,80, 80, tileSize, tileSize, null);
    }
}
