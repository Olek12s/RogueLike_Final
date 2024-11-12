package main.map;

import utilities.Sprite;
import utilities.SpriteSheet;

public class Tile
{
    private Sprite currentSprite;
    private boolean isColliding;

    public Tile(SpriteSheet spriteSheet, boolean isColliding)
    {
        this.currentSprite = extractRandomVariation(spriteSheet);
        this.isColliding = isColliding;
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
