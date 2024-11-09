package main.tile;

import utilities.Sprite;
import utilities.SpriteSheet;

public class Tile
{
    private Sprite currentSprite;
    private final int id;
    public boolean collision = false;

    public Tile(SpriteSheet spriteSheet, boolean collision, int id)
    {
        this.id = id;
        this.currentSprite = TileManager.extractRandomVariation(spriteSheet);
        this.collision = collision;
    }
    public int getId() {
        return id;
    }

    public Sprite getCurrentSprite() {
        return currentSprite;
    }
}
