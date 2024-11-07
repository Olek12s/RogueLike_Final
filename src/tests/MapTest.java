package tests;

import main.GameController;
import main.entity.Entity;
import main.item.Item;
import test.annotations.Before;
import test.annotations.Test;
import test.runner.Assertions;
import utilities.Position;

public class MapTest
{
    GameController gc;
    Item item;
    Entity entity;



    @Before
    public void initialize()
    {
        gc = new GameController();
        item = new Item(gc);
        entity = new Entity(gc,new Position(0*gc.tileManager.tileSize, 1*gc.tileManager.tileSize)); // Tile (0, 0)// Tile (0, 1)

        item.setWorldPosition(new Position(0*gc.tileManager.tileSize, 0*gc.tileManager.tileSize));

        gc.map.lyingItems.add(item);
        gc.map.entities.add(entity);
    }

    @Test
    public void removeItemFromTileTest()
    {
        // checking if item was added
        Assertions.assertTrue(gc.map.lyingItems.contains(item), "Item should be in the map before removal.");

        // removing item from tile (0, 0)
        gc.map.removeItemFromTile(new Position(0*gc.tileManager.tileSize, 0*gc.tileManager.tileSize));

        // checking if it was removed
        Assertions.assertFalse(gc.map.lyingItems.contains(item), "Item should be removed from the map.");
    }

    @Test
    public void removeEntityFromTileTest()
    {
        // checking if item was added
        Assertions.assertTrue(gc.map.entities.contains(entity), "Entity should be in the map before removal.");

        // removing item from tile (0, 1)
        gc.map.removeEntityFromTile(new Position(0*gc.tileManager.tileSize, 1*gc.tileManager.tileSize));

        // checking if it was removed
        Assertions.assertFalse(gc.map.entities.contains(entity), "Entity should be removed from the map.");
    }
}
