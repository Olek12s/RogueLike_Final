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
        Rectangle predictedHitboxPosition = new Rectangle(hitbox);
        boolean isColliding = false;

        switch (entity.getDirection())   // finding out future hitbox position
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
        int leftCol = predictedHitboxPosition.x / Tile.tileSize;
        int rightCol = (predictedHitboxPosition.x + predictedHitboxPosition.width) / Tile.tileSize;
        int topRow = predictedHitboxPosition.y /Tile.tileSize;
        int bottomRow = (predictedHitboxPosition.y + predictedHitboxPosition.height) / Tile.tileSize;

        // checking UP
        if (entity.getDirection() == UP || entity.getDirection() == UP_LEFT || entity.getDirection() == UP_RIGHT)
        {
            Tile tileLeftTop = gc.mapController.getCurrentMap().getTile(leftCol, topRow);
            Tile tileLeftBottom = gc.mapController.getCurrentMap().getTile(rightCol, topRow);

            if ((tileLeftTop != null && tileLeftTop.isColliding()) || (tileLeftBottom != null && tileLeftBottom.isColliding()))
            {
                isColliding = true;
            }
        }

        // checking DOWN
        if (entity.getDirection() == DOWN || entity.getDirection() == DOWN_LEFT || entity.getDirection() == DOWN_RIGHT)
        {
            Tile tileLeftBottom = gc.mapController.getCurrentMap().getTile(leftCol, bottomRow);
            Tile tileRightBottom = gc.mapController.getCurrentMap().getTile(rightCol, bottomRow);
            if ((tileLeftBottom != null && tileLeftBottom.isColliding()) || (tileRightBottom != null && tileRightBottom.isColliding()))
            {
                isColliding = true;
            }
        }

        // checking LEFT
        if (entity.getDirection() == LEFT || entity.getDirection() == UP_LEFT || entity.getDirection() == DOWN_LEFT)
        {
            Tile tileLeftTop = gc.mapController.getCurrentMap().getTile(leftCol, topRow);
            Tile tileLeftBottom = gc.mapController.getCurrentMap().getTile(leftCol, bottomRow);
            if ((tileLeftTop != null && tileLeftTop.isColliding()) || (tileLeftBottom != null && tileLeftBottom.isColliding()))
            {
                isColliding = true;
            }
        }

        // checking RIGHT
        if (entity.getDirection() == RIGHT || entity.getDirection() == UP_RIGHT || entity.getDirection() == DOWN_RIGHT)
        {
            Tile tileRightTop = gc.mapController.getCurrentMap().getTile(rightCol, topRow);
            Tile tileRightBottom = gc.mapController.getCurrentMap().getTile(rightCol, bottomRow);
            if ((tileRightTop != null && tileRightTop.isColliding()) || (tileRightBottom != null && tileRightBottom.isColliding()))
            {
                isColliding = true;
            }
        }
        //bugFix #3: before returning check when isColliding == true how many pixels are between hitbox and colliding tile, move Entity by this difference
        return isColliding;
    }

}
