package utilities;

import main.GameController;
import main.entity.Entity;
import main.item.Item;
import main.tile.Tile;

public class Collision
{
    GameController gc;
    public Collision(GameController gc)
    {
        this.gc = gc;
    }
    /*
    private boolean isColliding()
    {
        int predictedX = position.x;
        int predictedY = position.y;

        switch (direction)
        {
            case DOWN: predictedY += getMovementSpeed(speed);break;
            case UP: predictedY -= getMovementSpeed(speed);break;
            case LEFT: predictedX -= getMovementSpeed(speed);break;
            case RIGHT: predictedX += getMovementSpeed(speed);break;
            case UP_LEFT:
                predictedX -= Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                predictedY -= Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                break;
            case UP_RIGHT:
                predictedX += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                predictedY -= Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                break;
            case DOWN_LEFT:
                predictedX -= Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                predictedY += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                break;
            case DOWN_RIGHT:
                predictedX += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                predictedY += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                break;
        }
        int tileX = predictedX / gc.tileManager.tileSize;
        int tileY = predictedY / gc.tileManager.tileSize;

        Tile tile = gc.map.getMapTile(tileX, tileY);
        return tile != null && tile.collision;
    }
    */

    public boolean canPutItemOnTile(Item item, Tile tile)
    {
        // in future - check by hitbox overlapping

        return !tile.collision;
    }

    public boolean canPutEntityOnTile(Entity entity, Tile tile)
    {
        // in future - check by hitbox overlapping

        return !tile.collision;
    }





}
