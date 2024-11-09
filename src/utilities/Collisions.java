package utilities;

import main.GameController;
import main.entity.Entity;
import main.item.Item;
import main.tile.Tile;

import static main.Direction.*;

public class Collisions
{
    static GameController gc;
    public Collisions(GameController gc)
    {
        this.gc = gc;
    }

    public static boolean isColliding(Entity entity)
    {
        Position startPosition = entity.getWorldPosition();
        boolean isColliding = false;


            if (entity.isMoving)
            {
                switch (entity.direction)
                {
                case DOWN: startPosition.y += entity.getMovementSpeed(); break;
                case UP: startPosition.y -= entity.getMovementSpeed(); break;
                case LEFT: startPosition.x -= entity.getMovementSpeed(); break;
                case RIGHT: startPosition.x += entity.getMovementSpeed(); break;
                case UP_LEFT:
                    startPosition.x -=  Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    startPosition.y -=  Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    break;
                case UP_RIGHT:
                    startPosition.x += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    startPosition.y -= Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    break;
                case DOWN_LEFT:
                    startPosition.x -= Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    startPosition.y += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    break;
                case DOWN_RIGHT:
                    startPosition.x += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    startPosition.y += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                    break;
            }
            }
        int tileX = startPosition.x / gc.tileManager.tileSize;
        int tileY = startPosition.y / gc.tileManager.tileSize;
        boolean tileCollision = gc.map.getMapTile(tileX, tileY).collision;

        return !tileCollision;
    }


    public static boolean canPutItemOnTile(Item item, Tile tile)
    {
        // in future - check by hitbox overlapping

        return !tile.collision;
    }

    public static boolean canPutEntityOnTile(Entity entity, Tile tile)
    {
        // in future - check by hitbox overlapping

        return !tile.collision;
    }
}