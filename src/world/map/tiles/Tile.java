package world.map.tiles;

import utilities.FileManipulation;
import utilities.Position;
import utilities.Sprite;
import utilities.SpriteSheet;

import java.io.Serializable;

public class Tile implements Serializable
{
    public static int tileSize = 64;
    private final short id;
    private Sprite currentSprite;
    private boolean isColliding;
    private Position worldPosition;



    public int getId() {return id;}

    private static int counter = 0;
    public Tile(int id, Position position) throws InterruptedException
    {
        this.id = (short)id;
        this.worldPosition = position;
        this.currentSprite = TileManager.getTileObject(id).getRandomVariation(0);
        this.isColliding = TileManager.getTileObject(id).isColliding();
    }

    public Tile(Position position)
    {
        this.id = 0;
        this.worldPosition = position;
        SpriteSheet defaultSS = new SpriteSheet(FileManipulation.loadImage("resources/default/defaultTile"), 64);
        this.currentSprite = defaultSS.extractFirst();
    }

    public Sprite getCurrentSprite() {return currentSprite;}
    public boolean isColliding() {return isColliding;}



    public static Sprite extractRandomVariation(SpriteSheet spriteSheet)
    {
        if (spriteSheet.countSpriteVariations() == 1)
        {
            return spriteSheet.extractFirst();
        }
        else
        {
            int randomVariation = (int) (Math.random() * spriteSheet.countSpriteVariations());
            return spriteSheet.extractSpriteByVariation(randomVariation);
        }
    }

    public boolean doesContainPosition(Position position)
    {
        int startX = worldPosition.x;
        int startY = worldPosition.y;
        int endX = startX + tileSize;
        int endY = startY + tileSize;

        return position.x >= startX && position.x < endX && position.y >= startY && position.y < endY;
    }

    public boolean isCavePassage() {
        TileManager.TileID tileID = TileManager.TileID.fromId(id);
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
