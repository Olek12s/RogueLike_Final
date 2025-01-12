package world.map.tiles;

import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;

public class TileObject
{
    private Sprite sprite;
    private Sprite[] edgedSprites;
    private SpriteSheet spriteSheet;
    private SpriteSheet edgedSpriteSheet;
    private boolean isColliding;
    private String name;
    private int id;

    /*
    public Sprite getRandomVariation()
    {
        int variation = (int) (Math.random() * (spriteSheet.variations));

        return sprites[variation];
    }
     */

    public Sprite getSprite() {return sprite;}
    public boolean isCollidable() {return isColliding;}

    public void setSprite(Sprite sprite) {this.sprite = sprite;}

    public TileObject(SpriteSheet spriteSheet, boolean isColliding, String name, int id)
    {
        this.sprite = spriteSheet.extractFirst();
        this.spriteSheet = spriteSheet;
        this.isColliding = isColliding;
        this.name = name;
        this.id = id;
    }

    public TileObject(SpriteSheet spriteSheet, SpriteSheet edgedSpriteSheet, boolean isColliding, String name, int id)
    {
        this.sprite = spriteSheet.extractFirst();
        this.edgedSprites = new Sprite[edgedSpriteSheet.variations];
        this.spriteSheet = spriteSheet;
        this.edgedSpriteSheet = edgedSpriteSheet;
        this.isColliding = isColliding;
        this.name = name;
        this.id = id;

        for (int variation = 0; variation < edgedSpriteSheet.variations; variation++)
        {
            edgedSprites[variation] = edgedSpriteSheet.extractSprite(edgedSpriteSheet, 0, variation);
        }
    }
}
