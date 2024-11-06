package main.tile;

import utilities.Sprite;
import utilities.SpriteSheet;

public class Tile
{
    private SpriteSheet spriteSheet;
    private Sprite currentSprite;
    private Sprite[] spriteImages; // animations array
    private final int id;
    public boolean collision = false;

    public Tile(SpriteSheet spriteSheet, int id)
    {
        this.spriteSheet = spriteSheet;
        this.currentSprite = spriteSheet.extractFirst(spriteSheet);
        this.id = id;
    }

    public int getId() {return id;}
    public Sprite getCurrentSprite() {return currentSprite;}


    protected void loadSpriteImages()
    {
        int ticks = spriteSheet.countAnimationTicks();
        spriteImages = new Sprite[ticks];

        for (int tick = 0; tick < ticks; tick++)
        {
            spriteImages[tick] = spriteSheet.extractSprite(spriteSheet, tick);
        }
    }
}
