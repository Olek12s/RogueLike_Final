package main.tile;

import main.GameController;

import java.util.HashMap;

public class TileManager
{
    GameController gc;
    public final int tileSize = 32;
    private HashMap<Integer, Tile> tileMap;

    public TileManager(GameController gc)
    {
        this.gc = gc;
        tileMap = new HashMap<>();
    }

    public void addTile(Tile tile)
    {
        tileMap.put(tile.getId(), tile);
    }

    public Tile getTile(int id)
    {
        return tileMap.get(id);
    }
}
