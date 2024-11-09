package main.tile;

import main.GameController;
import utilities.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

import static utilities.FileManipulation.loadImage;

// one instance
public class TileManager
{
    GameController gc;

    public int tileSize = 64;
    public Map<Integer, Tile> tiles;

    public TileManager(GameController gc)
    {
        this.gc = gc;
        tiles = new HashMap<>();
        loadTiles();
    }

    private void loadTiles()
    {
        int i = 0;
        tiles.put(i, new Tile(new SpriteSheet(loadImage("resources/default/defaultTileTest"), tileSize), false, i++));
        tiles.put(i, new Tile(new SpriteSheet(loadImage("resources/default/defaultTileCollision"), tileSize), true, i++));
    }

    public Tile getRandomTile(int tileID)
    {
        Tile templateTile = tiles.get(tileID);
        if (templateTile != null)
        {
            return new Tile(templateTile.getSpriteSheet(), templateTile.collision, tileID);
        }
        return null;
    }
}
