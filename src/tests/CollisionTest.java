package tests;

import main.GameController;
import main.entity.Entity;
import main.item.Item;
import main.tile.Tile;
import test.annotations.Before;
import test.annotations.Test;
import test.runner.Assertions;
import utilities.Position;

public class CollisionTest
{
    GameController gc;
    Item item;
    Entity entity;

    Tile collidableTile;
    Tile nonCollidableTile;

    @Before
    public void initialize()
    {
        gc = new GameController();
        item = new Item(gc);
        entity = new Entity(gc, new Position(100, 100));

        collidableTile = gc.map.getMapTile(0,0);
        nonCollidableTile = gc.map.getMapTile(1,2);
    }

    @Test
    public void puttingItemOnTileTest()
    {
        Assertions.assertTrue(gc.collision.canPutItemOnTile(item, nonCollidableTile), "[It was expected to be possible to put an item on non collidable]");
        Assertions.assertFalse(gc.collision.canPutItemOnTile(item, collidableTile), "[It was expected to be not possible to put an item on collidable]");
    }

    @Test
    public void puttingEntityOnTileTest()
    {
        Assertions.assertTrue(gc.collision.canPutEntityOnTile(entity, nonCollidableTile), "[It was expected to be possible to put an entity on non collidable]");
        Assertions.assertFalse(gc.collision.canPutEntityOnTile(entity, collidableTile), "[It was expected to be not possible to put an entity on collidable]");
    }
}
