package main.entity;

import main.Direction;
import main.Updatable;
import main.map.Chunk;
import utilities.Collisions;

public class EntityUpdater implements Updatable
{
    private Entity entity;
    private int spriteCounter = 0;
    private int animationSpeed = 8;

    public Entity getEntity() {return entity;}

    public EntityUpdater(Entity entity)
    {
        this.entity = entity;

        entity.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        updateCurrentSprite();
        move();
        updateHitbox();
        updateChunkAssociation();
    }

    private void updateHitbox()
    {
        entity.getHitbox().centerPositionToEntity(entity);
    }

    public void updateCurrentSprite()
    {
        if (entity.isMoving)
        {
            spriteCounter = (spriteCounter + 1) % (entity.entityRenderer.spriteImages.length * animationSpeed);
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
            case DOWN:      entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][0]; break;
            case LEFT:      entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][1]; break;
            case RIGHT:     entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][2]; break;
            case UP:        entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][3]; break;
            case UP_LEFT:   entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][4]; break;
            case UP_RIGHT:  entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][5]; break;
            case DOWN_LEFT: entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][6]; break;
            case DOWN_RIGHT:entity.currentSprite = entity.entityRenderer.spriteImages[animationTick][7]; break;
            default:        entity.currentSprite = entity.entityRenderer.spriteImages[0][0]; break;
        }
    }

    protected void move()
    {
        if (entity.isMoving && !Collisions.isColliding(entity))
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

    private void updateChunkAssociation()
    {
        Chunk currentChunk = entity.getCurrentChunk();
        Chunk newChunk = entity.gc.mapController.getCurrentMap().getChunk(entity.getWorldPosition());

        if (newChunk != currentChunk)   // move entity to the new chunk
        {
            System.out.println("moved entity to the chunk: " + newChunk.toString());
            if (currentChunk != null)
            {
                currentChunk.removeEntity(entity);
            }
            newChunk.getEntities().add(entity);
            entity.setCurrentChunk(newChunk);
        }


    }
}
