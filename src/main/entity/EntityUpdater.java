package main.entity;

import main.Direction;
import main.controller.GameState;
import main.controller.Updatable;
import main.entity.player.Player;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.item.Item;
import utilities.Hitbox;
import utilities.Position;
import utilities.pathfinding.astar.AStar;
import world.map.Chunk;
import utilities.Collisions;

import java.util.ArrayList;

public class EntityUpdater implements Updatable
{
    private Entity entity;
    private int spriteCounter = 0;
    private int animationSpeed = 8;
    private int movementCounter = 0;
    private int updateStatisticsCounter = 0;
    private int pathUpdateCounter = 0;
    private int randomthreshold = 30;
    private GameState previousGameState;


    private ArrayList<Slot> equippedSlots = new ArrayList<>();

    public Entity getEntity() {return entity;}

    public EntityUpdater(Entity entity)
    {
        this.entity = entity;
        entity.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        if (entity != null && (entity.getLevel() == entity.gc.mapController.getCurrentMap().getLevel()))
        {
            updateEquippedSlots();
            updateStatistics();
            updateRegeneration();
            updateCurrentSprite();
            moveTowardsDirection();
            updateHitbox();
            updateChunkAssociation();
            updateAliveStatus();

            if (!(entity instanceof Player))
            {
                updateState();
                updateBehaviourBasedOnState();
                updateMeleeAttack(entity.gc.player);
            }
            if (entity instanceof Player)   // special case where previous gamestate is required. if previous gamestate is not needed - use PlayerUpdater class
            {
                updateHeldItemOnClosingInventory();
            }
            previousGameState = entity.gc.gameStateController.getCurrentGameState();
        }
    }

    public void initUpdate()
    {
        if (entity != null)
        {
            updateHitbox();
            updateChunkAssociation();
            updateAliveStatus();
        }
    }

    private void updateEquippedSlots()
    {
        equippedSlots.clear();
        Inventory entityInventory = entity.getInventory();

        equippedSlots.add(entityInventory.getHelmetSlot());
        equippedSlots.add(entityInventory.getChestplateSlot());
        equippedSlots.add(entityInventory.getPantsSlot());
        equippedSlots.add(entityInventory.getBootsSlot());
        equippedSlots.add(entityInventory.getShieldSlot());
        equippedSlots.add(entityInventory.getRing1Slot());
        equippedSlots.add(entityInventory.getRing2Slot());
        equippedSlots.add(entityInventory.getAmuletSlot());
    }

    public void updateRegeneration()
    {
        if (updateStatisticsCounter == 120) // once per 2 seconds
        {
            if (entity.statistics.getHitPoints() < entity.statistics.getMaxHitPoints())
            {
                int healUpAmount = entity.statistics.getHitPoints() + entity.statistics.getRegeneration();
                int newHitPoints = Math.min(healUpAmount, entity.statistics.getMaxHitPoints());
                entity.statistics.setHitPoints(newHitPoints);
            }
            updateStatisticsCounter = 0;
        }
        updateStatisticsCounter++;
    }

    private void updateHitbox()
    {
        entity.getHitbox().centerPositionToEntity(entity);
    }


    /**
     * Performs melee attack against target entity. If target is null - anything within attack hitbox will be damaged.
     * There's possibility to melee attack multiple targets at once, if target is null.
     * There's possibility to melee attack only one target, if target is specified.
     *
     * @param target - target to be damaged. If null - every target except source will be damaged.
     */
    public void updateMeleeAttack(Entity target)
    {
        if (target == entity.gc.player)
        {

        }
        else if (target == null)
        {

        }
        entity.updateAttackHitbox(entity.gc.player);
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

    public void updateAliveStatus()
    {
        if (entity.getCurrentHealth() <= 0) entity.setAlive(false);

        //pre-death actions

        if (entity.isAlive() == false)
        {
            entity.gc.drawables.remove(entity.entityRenderer);

            entity.entityUpdater = null;
            entity.entityRenderer = null;


            entity.getCurrentChunk().removeEntity(entity);
            entity.gc.updatables.remove(entity.entityUpdater);
            entity = null;
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

    protected void moveTowardsDirection()
    {
        if (entity.isMoving)
        {
            int moveX = 0;
            int moveY = 0;
            // !Collisions.isColliding(entity) && !Collisions.isCollidingWithOtherHitbox(entity);

            if (!Collisions.willCollide(entity) && !Collisions.willCollideWithOtherHitbox(entity))
            {
                if (entity.direction == Direction.UP)
                {
                    moveY -= entity.getMovementSpeed();
                }
                else if (entity.direction == Direction.DOWN)
                {
                    moveY += entity.getMovementSpeed();
                }
                else if (entity.direction == Direction.LEFT)
                {
                    moveX -= entity.getMovementSpeed();
                }
                else if (entity.direction == Direction.RIGHT)
                {
                    moveX += entity.getMovementSpeed();
                }
            }

            int movementSpeed = entity.getMovementSpeed();
            int entityWidth = entity.getHitbox().getWidth();
            int entityHeight = entity.getHitbox().getHeight();
            Position entityPosition = entity.getWorldPosition();

            boolean canMoveLeft =
                            Collisions.isPositionUncollidable(new Position(entityPosition.x - movementSpeed, entityPosition.y)) && // up-left
                            Collisions.isPositionUncollidable(new Position(entityPosition.x - movementSpeed, entityPosition.y + entityHeight)); // down-left


            boolean canMoveRight =
                            Collisions.isPositionUncollidable(new Position(entityPosition.x + entityWidth + movementSpeed, entityPosition.y)) && // top-right
                            Collisions.isPositionUncollidable(new Position(entityPosition.x + entityWidth + movementSpeed, entityPosition.y + entityHeight)); // down-right


            boolean canMoveUp =
                            Collisions.isPositionUncollidable(new Position(entityPosition.x, entityPosition.y - movementSpeed)) && // top-left
                            Collisions.isPositionUncollidable(new Position(entityPosition.x + entityWidth, entityPosition.y - movementSpeed)); // top-right


            boolean canMoveDown =
                            Collisions.isPositionUncollidable(new Position(entityPosition.x, entityPosition.y + entityHeight + movementSpeed)) && // down-left
                            Collisions.isPositionUncollidable(new Position(entityPosition.x + entityWidth, entityPosition.y + entityHeight + movementSpeed)); // down-right


            // If entity is stuck, change its direction to the diagonal
            changeDirectionToDiagonalOnStuck(entity, canMoveUp, canMoveDown, canMoveLeft, canMoveRight);

            if (entity.direction == Direction.UP_LEFT)
            {
               if (canMoveLeft) moveX -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
               if (canMoveUp) moveY -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }
            if (entity.direction == Direction.UP_RIGHT)
            {
               if (canMoveRight) moveX += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
               if (canMoveUp) moveY -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }
            if (entity.direction == Direction.DOWN_LEFT)
            {
               if (canMoveLeft) moveX -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
               if (canMoveDown) moveY += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }
            if (entity.direction == Direction.DOWN_RIGHT)
            {
               if (canMoveRight) moveX += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
               if (canMoveDown) moveY += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
            }

            entity.worldPosition.x += moveX;
            entity.worldPosition.y += moveY;
        }
    }

    private void changeDirectionToDiagonalOnStuck(Entity entity, boolean canMoveUp, boolean canMoveDown, boolean canMoveLeft, boolean canMoveRight)
    {
        if ((entity.direction == Direction.RIGHT && !canMoveRight)   ||
                (entity.direction == Direction.LEFT && !canMoveLeft) ||
                (entity.direction == Direction.UP && !canMoveUp)     ||
                (entity.direction == Direction.DOWN && !canMoveDown))
        {
            Position entityCenter = entity.getHitbox().getCenterWorldPosition();
            Position[] path = entity.getPathToFollow();
            if (path != null && path.length > 0)
            {
                Position target = path[0];
                Hitbox entityHitbox = entity.getHitbox();

                if (entityHitbox.getDiagonalLength() * 2 > entityCenter.distanceTo(path[path.length - 1]))   // to prevent wobbling on close contact with targeted entity
                {
                    return;
                }

                Direction newDirection = getNewDirection(entity, entityCenter, target);
                if (newDirection != null)
                {
                    entity.setDirection(newDirection);
                }
            }
        }
    }

    private Direction getNewDirection(Entity entity, Position entityCenter, Position target)
    {
        if (entity.direction == Direction.RIGHT)
        {
            if (target.y > entityCenter.y) return Direction.DOWN_RIGHT;
            if (target.y < entityCenter.y) return Direction.UP_RIGHT;
        }
        else if (entity.direction == Direction.LEFT)
        {
            if (target.y > entityCenter.y) return Direction.DOWN_LEFT;
            if (target.y < entityCenter.y) return Direction.UP_LEFT;
        }
        else if (entity.direction == Direction.UP)
        {
            if (target.x > entityCenter.x) return Direction.UP_RIGHT;
            if (target.x < entityCenter.x) return Direction.UP_LEFT;
        }
        else if (entity.direction == Direction.DOWN)
        {
            if (target.x > entityCenter.x) return Direction.DOWN_RIGHT;
            if (target.x < entityCenter.x) return Direction.DOWN_LEFT;
        }
        return null;
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

                if (randomIsMoving == 0 || entity.isImmobilised) entity.isMoving = false;
                else entity.isMoving = true;
            }
        }
        movementCounter++;
        if (movementCounter > 60 + randomCounterOffset) movementCounter = 0;
    }

    public void updateChunkAssociation()
    {
        Chunk currentChunk = entity.getCurrentChunk();
        Chunk newChunk = entity.gc.mapController.getCurrentMap().getChunk(entity.getWorldPosition());

        if (newChunk != currentChunk)   // move entity to the new chunk
        {
            if (currentChunk != null)
            {
                currentChunk.removeEntity(entity);
            }
            newChunk.addEntity(entity);
            entity.setCurrentChunk(newChunk);
        }
    }

    private void updateState()
    {
        int distanceToPlayer = (int) entity.getHitbox().getCenterWorldPosition().distanceTo(entity.gc.player.getHitbox().getCenterWorldPosition());

        if (distanceToPlayer < entity.getDetectionRadius())     // within detection radius
        {
            updateChasingPath(entity.gc.player);
            entity.setBehaviourState(BehaviourState.FOLLOW_PATH);
            entity.setAlerted(true);
        }
        else if (distanceToPlayer <= entity.getLoseInterestRadius() && entity.isAlerted())    // within lose interest radius
        {
            updateChasingPath(entity.gc.player);
            entity.setBehaviourState(BehaviourState.FOLLOW_PATH);
        }
        else      // out of any radius
        {
            entity.setAlerted(false);
        }
    }

    private void updateChasingPath(Entity target)
    {
        int randomNumber = (int) (Math.random() * (randomthreshold + 1));

        if (pathUpdateCounter >= randomNumber)
        {
            entity.setPathToFollow(AStar.getPathToEntity(entity, target));
            pathUpdateCounter = 0;
        }
        pathUpdateCounter++;
    }



    private void updateBehaviourBasedOnState()
    {
        BehaviourState behaviourState = entity.getBehaviourState();

        switch (behaviourState)
        {
            case WANDER:
                updateRandomizedMovement();
                break;

            case FOLLOW_PATH:
                followPath();
                break;
        }
    }

    private void followPath()
    {
        Position[] path = entity.getPathToFollow();

        if (path == null || path.length == 0) {
            entity.isMoving = false;
            return;
        }

        // Get the last point in the path
        Position target;
        if (path.length > 1) target = path[1];
        else
        {
            entity.setBehaviourState(BehaviourState.WANDER);
            return;
        }

        // Current position of the entity's hitbox center
        Position currentPosition = entity.hitbox.getWorldPosition();

        // Calculate the direction vector
        int dx = target.x - currentPosition.x;
        int dy = target.y - currentPosition.y;
        double distance = Math.sqrt((dx * dx) + (dy * dy));

        // Determine the direction based on dx and dy
        if (distance < entity.getMovementSpeed()/2)
        {
            // Target reached, remove it from the path
            Position[] newPath = new Position[path.length - 1];
            System.arraycopy(path, 1, newPath, 0, path.length - 1);  // Skip the first element since it's already reached
            entity.setPathToFollow(newPath);

            // If the path is now empty, stop moving
            if (newPath.length == 0) {
                entity.isMoving = false;
            }
            return;
        }

        // Normalize the direction vector to determine the direction
        Direction direction;
        double angle = Math.toDegrees(Math.atan2(-dy, dx)); // Y-axis is inverted in most 2D coordinate systems

        if (angle >= -22.5 && angle < 22.5) {
            direction = Direction.RIGHT;
        } else if (angle >= 22.5 && angle < 67.5) {
            direction = Direction.UP_RIGHT;
        } else if (angle >= 67.5 && angle < 112.5) {
            direction = Direction.UP;
        } else if (angle >= 112.5 && angle < 157.5) {
            direction = Direction.UP_LEFT;
        } else if (angle >= -67.5 && angle < -22.5) {
            direction = Direction.DOWN_RIGHT;
        } else if (angle >= -112.5 && angle < -67.5) {
            direction = Direction.DOWN;
        } else if (angle >= -157.5 && angle < -112.5) {
            direction = Direction.DOWN_LEFT;
        } else {
            direction = Direction.LEFT;
        }

        // Update the entity's direction and set it to moving
        entity.setDirection(direction);
        entity.isMoving = true;
    }

    /**
     * Function that ensures, if player closed inventory, then held item is released on the ground below the player.
     */
    public void updateHeldItemOnClosingInventory()
    {
        if (entity.getInventory().getHeldItem() == null) return;

        if (previousGameState == GameState.INVENTORY && entity.gc.gameStateController.getCurrentGameState() != GameState.INVENTORY)
        {
            Item heldItem = entity.inventory.getHeldItem();
            entity.getInventory().dropItemOnGround(heldItem);
        }
    }

    /**
     * Updates entity's current statistics based on effects, currently equipped items, etc...
     */
    public void updateStatistics()
    {
        updateStatisticsArmor();
        updateStatisticsMovementSpeed();
    }

    private void updateStatisticsArmor()
    {
        int armorFromEquippedItems = 0;
        int magicArmorFromEquippedItems = 0;

        for (Slot slot : equippedSlots)
        {
            if (slot.getStoredItem() == null) continue;
            armorFromEquippedItems += slot.getStoredItem().getStatistics().getArmor();
            magicArmorFromEquippedItems += slot.getStoredItem().getStatistics().getMagicalArmor();
        }
        entity.statistics.setArmour(armorFromEquippedItems + entity.statistics.getBaseArmour());
        entity.statistics.setMagicArmour(magicArmorFromEquippedItems + entity.statistics.getBaseMagicArmour());
    }

    public void updateStatisticsMovementSpeed()
    {
        int updatedMovementSpeed = entity.getMaxMovementSpeed();
        for (Slot slot : equippedSlots)
        {
            if (slot.getStoredItem() == null) continue;
            updatedMovementSpeed *= (float)(1 - slot.getStoredItem().getStatistics().getMovementSpeedPenalty());
        }
        if (entity.isCrouching()) updatedMovementSpeed /= 3;
        entity.statistics.setCurrentMovementSpeed(updatedMovementSpeed);
    }
}
