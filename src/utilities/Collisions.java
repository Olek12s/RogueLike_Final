package utilities;

import main.GameController;
import main.entity.Entity;
import main.item.Item;
import main.tile.Tile;

import java.awt.*;

import static main.Direction.*;

public class Collisions
{
    static GameController gc;
    public Collisions(GameController gc)
    {
        this.gc = gc;
    }

    private static boolean isCollidingWithTile(Entity entity)
    {
        Rectangle hitbox = entity.hitbox.hitboxRect;
        Rectangle predictedHitboxPosition = new Rectangle(hitbox);
        boolean isColliding = false;

        switch (entity.direction)   // finding out future hitbox position
        {
            case DOWN:
                predictedHitboxPosition.y += entity.getMovementSpeed();
                break;
            case UP:
                predictedHitboxPosition.y -= entity.getMovementSpeed();
                break;
            case LEFT:
                predictedHitboxPosition.x -= entity.getMovementSpeed();
                break;
            case RIGHT:
                predictedHitboxPosition.x += entity.getMovementSpeed();
                break;
            case UP_LEFT:
                predictedHitboxPosition.x -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                predictedHitboxPosition.y -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case UP_RIGHT:
                predictedHitboxPosition.x += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                predictedHitboxPosition.y -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case DOWN_LEFT:
                predictedHitboxPosition.x -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                predictedHitboxPosition.y += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case DOWN_RIGHT:
                predictedHitboxPosition.x += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                predictedHitboxPosition.y += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
        }

        // checking tile's collision in predicted hitbox position
        int leftCol = predictedHitboxPosition.x / gc.tileManager.tileSize;
        int rightCol = (predictedHitboxPosition.x + predictedHitboxPosition.width) / gc.tileManager.tileSize;
        int topRow = predictedHitboxPosition.y / gc.tileManager.tileSize;
        int bottomRow = (predictedHitboxPosition.y + predictedHitboxPosition.height) / gc.tileManager.tileSize;

        // checking UP
        if (entity.direction == UP || entity.direction == UP_LEFT || entity.direction == UP_RIGHT)
        {
            Tile tileLeftTop = gc.map.getMapTile(leftCol, topRow);
            Tile tileLeftBottom = gc.map.getMapTile(rightCol, topRow);

            if ((tileLeftTop != null && tileLeftTop.collision) || (tileLeftBottom != null && tileLeftBottom.collision))
            {
                isColliding = true;
            }
        }

        // checking DOWN
        if (entity.direction == DOWN || entity.direction == DOWN_LEFT || entity.direction == DOWN_RIGHT)
        {
            Tile tileLeftBottom = gc.map.getMapTile(leftCol, bottomRow);
            Tile tileRightBottom = gc.map.getMapTile(rightCol, bottomRow);
            if ((tileLeftBottom != null && tileLeftBottom.collision) || (tileRightBottom != null && tileRightBottom.collision))
            {
                isColliding = true;
            }
        }

        // checking LEFT
        if (entity.direction == LEFT || entity.direction == UP_LEFT || entity.direction == DOWN_LEFT)
        {
            Tile tileLeftTop = gc.map.getMapTile(leftCol, topRow);
            Tile tileLeftBottom = gc.map.getMapTile(leftCol, bottomRow);
            if ((tileLeftTop != null && tileLeftTop.collision) || (tileLeftBottom != null && tileLeftBottom.collision))
            {
                isColliding = true;
            }
        }

        // checking RIGHT
        if (entity.direction == RIGHT || entity.direction == UP_RIGHT || entity.direction == DOWN_RIGHT)
        {
            Tile tileRightTop = gc.map.getMapTile(rightCol, topRow);
            Tile tileRightBottom = gc.map.getMapTile(rightCol, bottomRow);
            if ((tileRightTop != null && tileRightTop.collision) || (tileRightBottom != null && tileRightBottom.collision))
            {
                isColliding = true;
            }
        }
        //bugFix #3: before returning check when isColliding == true how many pixels are between hitbox and colliding tile, move Entity by this difference
        return isColliding;
    }

    public static boolean isCollidingWithEntity(Entity entityOther)
    {
        System.out.println(gc.map.entities.size());
        for (Entity entity : gc.map.entities)
        {
            if (entity != entityOther && entity.hitbox.hitboxRect.intersects(entityOther.hitbox.hitboxRect))
            {
                System.out.println("collision with other entity");
                return true;
            }
        }
        return false;
    }

    public static boolean isColliding(Entity entity)
    {
        //boolean isColliding = false;
        isCollidingWithTile(entity);
        return isCollidingWithTile(entity) || isCollidingWithEntity(entity);
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