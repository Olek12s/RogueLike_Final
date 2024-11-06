package main.tile;

import utilities.Sprite;
import utilities.SpriteSheet;

public class Tile
{
    private SpriteSheet spriteSheet;
    private Sprite currentSprite;
    private final int id;
    public boolean collision = false;

    public Tile(SpriteSheet spriteSheet, boolean collision, int id)
    {
        this.spriteSheet = spriteSheet;
        this.id = id;
        this.currentSprite = extractRandomVariation();
        this.collision = collision;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public int getId() {
        return id;
    }

    public Sprite getCurrentSprite() {
        return currentSprite;
    }

    private Sprite extractRandomVariation()
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
