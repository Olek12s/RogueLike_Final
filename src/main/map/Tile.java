package main.map;

import utilities.FileManipulation;
import utilities.Sprite;
import utilities.SpriteSheet;

public class Tile
{
    public static int tileSize = 64;
    private Sprite currentSprite;
    private boolean isColliding;

    public Tile(SpriteSheet spriteSheet, boolean isColliding)
    {
        this.currentSprite = extractRandomVariation(spriteSheet);
        this.isColliding = isColliding;
    }

    public Tile()
    {
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
}
