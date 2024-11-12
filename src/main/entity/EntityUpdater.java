package main.entity;

import main.Direction;
import main.Updatable;

public class EntityUpdater implements Updatable
{
    private Entity entity;
    private int spriteCounter = 0;
    private int animationSpeed = 6;

    public Entity getEntity() {return entity;}

    public EntityUpdater(Entity entity)
    {
        this.entity = entity;
        //entity.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        updateCurrentSprite();
        move();
    }

    private void updateCurrentSprite()
    {
        if (entity.isMoving)
        {
            spriteCounter = (spriteCounter + 1) % (entity.renderer.spriteImages.length * animationSpeed);
            int currentAnimationTick = (spriteCounter / animationSpeed);
            if (currentAnimationTick == 0) currentAnimationTick = 1;
            changeSprite(entity.direction, currentAnimationTick);
        }
        else
        {
            spriteCounter = 0;
            changeSprite(entity.direction, 0);
        }
    }

    protected void changeSprite(Direction direction, int animationTick)
    {
        switch (direction)
        {
            case DOWN:      entity.currentSprite = entity.renderer.spriteImages[animationTick][0]; break;
            case LEFT:      entity.currentSprite = entity.renderer.spriteImages[animationTick][1]; break;
            case RIGHT:     entity.currentSprite = entity.renderer.spriteImages[animationTick][2]; break;
            case UP:        entity.currentSprite = entity.renderer.spriteImages[animationTick][3]; break;
            case UP_LEFT:   entity.currentSprite = entity.renderer.spriteImages[animationTick][4]; break;
            case UP_RIGHT:  entity.currentSprite = entity.renderer.spriteImages[animationTick][5]; break;
            case DOWN_LEFT: entity.currentSprite = entity.renderer.spriteImages[animationTick][6]; break;
            case DOWN_RIGHT:entity.currentSprite = entity.renderer.spriteImages[animationTick][7]; break;
            default:        entity.currentSprite = entity.renderer.spriteImages[0][0]; break;
        }
    }

    protected void move()
    {
        if (entity.isMoving /*&& !Collisions.isColliding(this)*/)
        {
            if (entity.direction == Direction.UP_LEFT)
            {
                entity.worldPosition.x -= Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                entity.worldPosition.y -= Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (entity.direction == Direction.UP_RIGHT)
            {
                entity.worldPosition.x += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                entity.worldPosition.y -= Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (entity.direction == Direction.DOWN_LEFT)
            {
                entity.worldPosition.x -= Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                entity.worldPosition.y += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (entity.direction == Direction.DOWN_RIGHT)
            {
                entity.worldPosition.x += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
                entity.worldPosition.y += Math.max((int)(entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (entity.direction == Direction.DOWN)
            {
                entity.worldPosition.y += entity.getMovementSpeed();
            }
            else if (entity.direction == Direction.LEFT)
            {
                entity.worldPosition.x -= entity.getMovementSpeed();
            }
            else if (entity.direction == Direction.RIGHT)
            {
                entity.worldPosition.x += entity.getMovementSpeed();
            }
            else if (entity.direction == Direction.UP)
            {
                entity.worldPosition.y -= entity.getMovementSpeed();
            }
        }
    }
}
