package utilities;

import main.GameController;
import main.entity.Entity;
import main.map.Tile;

import java.awt.*;

import static main.Direction.*;
import static main.Direction.DOWN_RIGHT;

public class Collisions
{
    static GameController gc;

    public Collisions(GameController gc)
    {
        this.gc = gc;
    }

    public static boolean isColliding(Entity entity)
    {
        Rectangle hitbox = entity.getHitbox().getHitboxRect();
        Rectangle preditctedHitbox = new Rectangle(hitbox);
        boolean isColliding = false;

        switch (entity.getDirection())   // finding out future hitbox position
        {
            case DOWN:
                preditctedHitbox.y += entity.getMovementSpeed();
                break;
            case UP:
                preditctedHitbox.y -= entity.getMovementSpeed();
                break;
            case LEFT:
                preditctedHitbox.x -= entity.getMovementSpeed();
                break;
            case RIGHT:
                preditctedHitbox.x += entity.getMovementSpeed();
                break;
            case UP_LEFT:
                preditctedHitbox.x -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case UP_RIGHT:
                preditctedHitbox.x += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case DOWN_LEFT:
                preditctedHitbox.x -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case DOWN_RIGHT:
                preditctedHitbox.x += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
        }

        try
        {
            Tile tileUpLeft = gc.mapController.getCurrentMap().getTile(preditctedHitbox.x, preditctedHitbox.y);
            Tile tileDownLeft = gc.mapController.getCurrentMap().getTile(preditctedHitbox.x, preditctedHitbox.y + preditctedHitbox.height);
            Tile tileUpRight = gc.mapController.getCurrentMap().getTile(preditctedHitbox.x + preditctedHitbox.width, preditctedHitbox.y);
            Tile tileDownRight = gc.mapController.getCurrentMap().getTile(preditctedHitbox.x + preditctedHitbox.width, preditctedHitbox.y + preditctedHitbox.height);
            if (tileUpLeft.isColliding() == true || tileDownLeft.isColliding() == true || tileUpRight.isColliding() == true || tileDownRight.isColliding() == true)
            {
                isColliding = true;
            }
        }
        catch (IndexOutOfBoundsException ex)    // out of map
        {
            isColliding = true;
        }

        return isColliding;
    }

}
