package world.map;

import utilities.FileManipulation;
import utilities.Sprite;
import utilities.SpriteSheet;

public class Tile
{
    public static int tileSize = 64;
    private final int id;
    private Sprite currentSprite;
    private boolean isColliding;

    public int getId() {return id;}

    public Tile(int id)
    {
        this.id = id;
        this.currentSprite = TileManager.getTileObject(id).getRandomVariation(0);
        this.isColliding = TileManager.getTileObject(id).isColliding();
        //this.currentSprite = extractRandomVariation(TileManager.getTileObject(id).getSpriteSheet());
        //this.isColliding = false;
    }

    public Tile()
    {
        this.id = 0;
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
