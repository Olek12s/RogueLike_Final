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
    private int movementCounter = 0;

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
        updateRandomizedMovement();
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
            //spriteCounter = (spriteCounter + 1) % (entity.entityRenderer.spriteImages.length * animationSpeed);
            spriteCounter = (spriteCounter + 1) % (EntityRenderer.getSpriteImagesByID(entity.getID()).length * animationSpeed);
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
            case DOWN: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][0]; break;
            case LEFT: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][1]; break;
            case RIGHT: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][2]; break;
            case UP: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][3]; break;
            case UP_LEFT: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][4]; break;
            case UP_RIGHT: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][5]; break;
            case DOWN_LEFT: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][6]; break;
            case DOWN_RIGHT: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[animationTick][7]; break;
            default: entity.currentSprite = EntityRenderer.getSpriteImagesByID(entity.getID())[0][0]; break;
        }
    }

    protected void move()
    {
        if (entity.isMoving && !Collisions.isColliding(entity) && !Collisions.isCollidingWithOtherHitbox(entity))
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

    private void updateRandomizedMovement()
    {
        int randomCounterOffset = (int) (Math.random() * 11) - 5;   // <-5, 5>
        if (movementCounter == 60 + randomCounterOffset)  // 60 - once per second
        {
            if (!getClass().getCanonicalName().contains("Player"))
            {
                int randomDirection = (int) (Math.random() * 8);    // 1/8 chance to not change direction to other
                int randomIsMoving = (int) (Math.random() * 8);     // 1/8 chance to change to not moving

                switch (randomDirection)
                {
                    case 0: entity.direction = Direction.DOWN; break;
                    case 1: entity.direction = Direction.LEFT; break;
                    case 2: entity.direction = Direction.RIGHT; break;
                    case 3: entity.direction = Direction.UP; break;
                    case 4: entity.direction = Direction.UP_LEFT; break;
                    case 5: entity.direction = Direction.UP_RIGHT; break;
                    case 6: entity.direction = Direction.DOWN_LEFT; break;
                    case 7: entity.direction = Direction.DOWN_RIGHT; break;
                }

                if (randomIsMoving == 0) entity.isMoving = false;
                else entity.isMoving = true;
            }
        }
        movementCounter++;
        if (movementCounter > 60 + randomCounterOffset) movementCounter = 0;
    }

    private void updateChunkAssociation()
    {
        Chunk currentChunk = entity.getCurrentChunk();
        Chunk newChunk = entity.gc.mapController.getCurrentMap().getChunk(entity.getWorldPosition());

        if (newChunk != currentChunk)   // move entity to the new chunk
        {
            if (currentChunk != null)
            {
                currentChunk.removeEntity(entity);
            }
            newChunk.getEntities().add(entity);
            entity.setCurrentChunk(newChunk);
        }


    }
}
