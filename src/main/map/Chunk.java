package main.map;

import main.entity.Entity;
import main.item.Item;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

public class Chunk
{
    private static final int chunkSize = 16;
    private Tile[][] tiles;
    private Position chunkWorldPosition;
    private List<Entity> entities;
    private List<Item> items;

    public Chunk(Position chunkWorldPosition)
    {
        this.chunkWorldPosition = chunkWorldPosition;
        this.tiles = new Tile[chunkSize][chunkSize];
        this.entities = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public Tile[][] getTiles() {return tiles;}
    public Position getChunkWorldPosition() {return chunkWorldPosition;}
    public List<Entity> getEntities() {return entities;}
    public List<Item> getItems() {return items;}
    public void removeEntity(Entity entity) {entities.remove(entity);}
    public void removeItem(Item item) {items.remove(item);}
    public static int getChunkSize() {return chunkSize;}
}
