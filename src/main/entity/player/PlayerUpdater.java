package main.entity.player;

import main.Direction;
import main.Updatable;
import main.entity.Entity;
import main.entity.EntityUpdater;

public class PlayerUpdater extends EntityUpdater implements Updatable
{
    Entity entity;

    public PlayerUpdater(Entity entity)
    {
        super(entity);
        this.entity = entity;

        entity.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        updatePlayerDirection();
    }

    private void updatePlayerDirection()
    {
        entity.isMoving = false;
        if (entity.gc.keyHandler.W_PRESSED && entity.gc.keyHandler.A_PRESSED || entity.gc.keyHandler.UP_PRESSED && entity.gc.keyHandler.LEFT_PRESSED)     // Direction Up-Left
        {
            entity.setDirection(Direction.UP_LEFT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.W_PRESSED && entity.gc.keyHandler.D_PRESSED || entity.gc.keyHandler.UP_PRESSED && entity.gc.keyHandler.RIGHT_PRESSED)     // Direction Up-Right
        {
            entity.setDirection(Direction.UP_RIGHT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.S_PRESSED && entity.gc.keyHandler.A_PRESSED || entity.gc.keyHandler.DOWN_PRESSED && entity.gc.keyHandler.LEFT_PRESSED)     // Direction Down-Left
        {
            entity.setDirection(Direction.DOWN_LEFT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.S_PRESSED && entity.gc.keyHandler.D_PRESSED || entity.gc.keyHandler.DOWN_PRESSED && entity.gc.keyHandler.RIGHT_PRESSED)     // Direction Down-Right
        {
            entity.setDirection(Direction.DOWN_RIGHT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.S_PRESSED || entity.gc.keyHandler.DOWN_PRESSED)   // Direction Down
        {
            entity.setDirection(Direction.DOWN);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.A_PRESSED || entity.gc.keyHandler.LEFT_PRESSED)  // Direction Left
        {
            entity.setDirection(Direction.LEFT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.D_PRESSED || entity.gc.keyHandler.RIGHT_PRESSED) // Direction right
        {
            entity.setDirection(Direction.RIGHT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.W_PRESSED || entity.gc.keyHandler.UP_PRESSED)   // Direction up
        {
            entity.setDirection(Direction.UP);
            entity.isMoving = true;
        }
    }
}
