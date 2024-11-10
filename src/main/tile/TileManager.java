package main.tile;

import main.GameController;
import utilities.Sprite;
import utilities.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

import static utilities.FileManipulation.loadImage;

// one instance
public class TileManager
{
    GameController gc;

    public static int tileSize = 64;
    public Map<Integer, Tile> tiles;
    public Map<Integer, SpriteSheet> spriteSheets;

    public TileManager(GameController gc)
    {
        this.gc = gc;
        tiles = new HashMap<>();
        spriteSheets = new HashMap<>();
        loadTiles();
    }

    private void putIntoMaps(int ID, Tile tile, SpriteSheet spriteSheet)
    {
        tiles.put(ID, tile);
        spriteSheets.put(ID, spriteSheet);
    }

    private void loadTiles()
    {
        int i = 0;
        SpriteSheet spriteSheet = new SpriteSheet(loadImage("resources/default/defaultTileTest"), tileSize);
        putIntoMaps(i, new Tile(spriteSheet, false, i++), spriteSheet);
        spriteSheet = new SpriteSheet(loadImage("resources/default/defaultTileCollision"), tileSize);
        putIntoMaps(i, new Tile(spriteSheet, true, i++), spriteSheet);
    }

    public Tile getTile(int tileID)
    {
        Tile templateTile = tiles.get(tileID);
        if (templateTile != null)
        {
            return new Tile(spriteSheets.get(templateTile.getId()), templateTile.collision, tileID);
        }
        return null;
    }

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
