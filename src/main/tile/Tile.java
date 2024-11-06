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
        System.out.println("A");
        this.spriteSheet = spriteSheet;
        //this.currentSprite = spriteSheet.extractFirst();
        this.currentSprite = extractRandomVariation();
        this.id = id;
        loadSpriteImages();
    }

    public int getId() {return id;}
    public Sprite getCurrentSprite() {return currentSprite;}


    protected void loadSpriteImages()
    {
        int ticks = spriteSheet.countAnimationTicks();
        spriteImages = new Sprite[ticks];

        for (int tick = 0; tick < ticks; tick++)
        {
            spriteImages[tick] = spriteSheet.extractSprite(tick);
        }
    }

    private Sprite extractRandomVariation()
    {
       if (spriteSheet.variations == 1) return spriteSheet.extractFirst();
       else
       {
           int randomTick = (int) (Math.random() * spriteSheet.variations) + 0;
           return spriteSheet.extractSprite(randomTick);
       }
    }
}
