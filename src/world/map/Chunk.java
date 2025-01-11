package world.map;

import main.entity.Entity;
import main.item.Item;
import utilities.Position;
import world.map.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Chunk
{
    private static final int chunkSize = 8;
    private Tile[][] tiles;
    private Position chunkWorldPosition;
    private List<Entity> entities;
    private List<Item> items;
    private int xIndex;
    private int yIndex;
    MapLevels maplevel;

    public Chunk(Position chunkWorldPosition, Tile[][] chunkTiles, int xIndex, int yIndex, MapLevels mapLevel)
    {
        this.chunkWorldPosition = chunkWorldPosition;
        //this.tiles = new Tile[chunkSize][chunkSize];
        this.entities = new ArrayList<>();
        this.items = new ArrayList<>();
        this.tiles = chunkTiles;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.maplevel = mapLevel;
    }

    public Tile[][] getTiles() {return tiles;}

    public void setTiles(Tile[][] tiles)
    {
        this.tiles = tiles;
    }

    public Position getChunkWorldPosition() {return chunkWorldPosition;}
    public List<Entity> getEntities() {return entities;}
    public void addEntity(Entity entity)
    {
        if (!entities.contains(entity))
        {
            entities.add(entity);
        }
    }
    public List<Item> getItems() {return items;}
    public void removeEntity(Entity entity) {entities.remove(entity);}
    public void removeItem(Item item) {items.remove(item);}
    public static int getChunkSize() {return chunkSize;}

    public int getxIndex() {return xIndex;}
    public int getyIndex() {return yIndex;}

    public int getChunkNumX()
    {
        int chunkPixelSize = chunkSize * Tile.tileSize;
        return  chunkWorldPosition.x / chunkPixelSize;
    }
    public int getChunkNumY()
    {
        int chunkPixelSize = chunkSize * Tile.tileSize;
        return  chunkWorldPosition.y / chunkPixelSize;
    }

    @Override
    public String toString()
    {
        int chunkPixelSize = chunkSize * Tile.tileSize;
        int chunkX = chunkWorldPosition.x / chunkPixelSize;
        int chunkY = chunkWorldPosition.y / chunkPixelSize;
        return "(" + chunkX + ", " + chunkY + ")";
    }
}
