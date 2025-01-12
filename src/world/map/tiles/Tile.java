package world.map.tiles;

import utilities.FileManipulation;
import utilities.Position;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tile implements Serializable
{
    private TileObject tileObject;
    public static int tileSize = 64;
    private final short id;
    //private Sprite currentSprite;
    //private boolean isColliding;
    private Position worldPosition;


    public int getId() {return id;}
    public boolean isColliding() {return tileObject.isCollidable();}

    private static int counter = 0;
    public Tile(int id, Position position) throws InterruptedException
    {
        this.id = (short)id;
        this.worldPosition = position;
        this.tileObject = TileManager.getTileObject(id);
    }

    public Tile(Position position)
    {
        this.id = (short) TileID.DEFAULT_TILE.getId();
        this.worldPosition = position;
        SpriteSheet defaultSS = new SpriteSheet(FileManipulation.loadImage("resources/default/defaultTile"), 64);
        this.tileObject = TileManager.getTileObject(id);
        this.tileObject.setSprite(defaultSS.extractFirst());
    }

    public Sprite getCurrentSprite() {return TileManager.getTileObject(id).getSprite();}

    public boolean doesContainPosition(Position position)
    {
        int startX = worldPosition.x;
        int startY = worldPosition.y;
        int endX = startX + tileSize;
        int endY = startY + tileSize;

        return position.x >= startX && position.x < endX && position.y >= startY && position.y < endY;
    }

    public boolean isCavePassage() {
        TileID tileID = TileID.fromId(id);
        switch (tileID)
        {
            case CAVE_ENTRANCE,
                 CAVE_EXIT,
                 CAVE_DEEP_ENTRANCE,
                 CAVE_DEEP_EXIT,
                 CAVE_RUINS_ENTRANCE,
                 CAVE_RUINS_EXIT-> {return true;}
            default -> {return false;}
        }
    }

}
