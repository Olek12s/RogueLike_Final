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
import world.map.MapController;

import java.util.ArrayList;
import java.util.List;

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

    private int remainingKnockbackCounter = 0;
    private int knockbackStrength = 0;
    Direction knockbackDirection;

    private int attackPreparationCounter = 0;
    private int attackRestCounter = 0;
    private int attackTimeCounter = 0;
    public static final int ATTACK_TIME = 5;    // 5 ticks
    private List<Entity> damagedEntities;


    private ArrayList<Slot> equippedSlots = new ArrayList<>();

    public Entity getEntity() {return entity;}
    public int getAttackPreparationCounter() {return attackPreparationCounter;}
    public void setAttackPreparationCounter(int attackPreparationCounter) {this.attackPreparationCounter = attackPreparationCounter;}
    public int getAttackRestCounter() {return attackRestCounter;}
    public void setAttackRestCounter(int attackRestCounter) {this.attackRestCounter = attackRestCounter;}
    public int getAttackTimeCounter() {return attackTimeCounter;}
    public void setAttackTimeCounter(int attackTimeCounter) {this.attackTimeCounter = attackTimeCounter;}

    public EntityUpdater(Entity entity)
    {
        this.entity = entity;
        entity.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        if (entity != null && (entity.getLevel() == entity.gc.mapController.getCurrentMap().getLevel()) && entity.isAlive())
        {
            updateEquippedSlots();
            updateStatistics();
            updateRegeneration();
            updateCurrentSprite();
            moveTowardsDirection();
            updateHitbox();
            updateChunkAssociation();
            updateKnockback();

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
        updateAliveStatus();
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
        if (target == entity.gc.player) // attack only player if intersects attackHitbox
        {
            if (entity.distanceBetween(target) <= entity.getCurrentBeltSlot().getStoredItem().getMaxMeleeAttackRange())
            {
                entity.setDuringMeleeAttack(true);
            }
        }
        if (!entity.isDuringMeleeAttack()) return;
        else if (target == null)    // attack every target which intersects with attackHitbox
        {

        }
        updateAttackHitbox();
    }

    /**
     * Executes the melee attack. Creates a hitbox for short period of time (5 ticks).
     * There's delay 'attackPreparationCounter' for every item, which indicates how many game ticks
     * need to pass before creating hitbox. Another Hitbox cannot be created for next 'attackRestCounter' ticks.
     *
     * 1) preparing for attack
     * 2) performing attack for 5 ticks
     * 3) resting after attack
     */
    public void updateAttackHitbox()
    {
        if (entity.getItemHeldDuringAttack() == null) entity.setItemHeldDuringAttack(entity.getInventory().getItemAtFromBelt(entity.getCurrentBeltSlotIndex()));
        if (entity.getItemHeldDuringAttack() == null) entity.setItemHeldDuringAttack(entity.getBareHands());
        if (damagedEntities == null) damagedEntities = new ArrayList<>();
        if (entity.isAlive() == false) return; // can't attack if dead
        if (attackPreparationCounter < entity.getItemHeldDuringAttack().getAttackPreparationTime())
        {
            attackPreparationCounter++;
            //return; // can't attack when preparing
        }
        else if (attackTimeCounter < ATTACK_TIME)    // melee attack period
        {
            attackTimeCounter++;
            entity.createAttackHitbox();
            damageEntitiesInHitboxRange(entity.getAttackHitbox(), entity.getItemHeldDuringAttack());
        }
        else if (attackRestCounter < entity.getItemHeldDuringAttack().getAttackRestTime())
        {
            if (entity.getAttackHitbox() != null) entity.setAttackHitbox(null);
            attackRestCounter++;
            return; // can't attack when resting
        }
        else
        {
            entity.setAttackHitbox(null);
            entity.setDuringMeleeAttack(false);
            entity.setItemHeldDuringAttack(null);
            attackRestCounter = 0;
            attackPreparationCounter = 0;
            attackTimeCounter = 0;
            damagedEntities = null;
        }
    }

    public void damageEntitiesInHitboxRange(Hitbox attackHitbox, Item weapon)
    {
        ArrayList<Chunk> chunks = new ArrayList<>();
        Chunk sourceChunk = MapController.getCurrentMap().getChunk(attackHitbox.getCenterWorldPosition());
        chunks.add(sourceChunk);
        chunks.addAll(MapController.getCurrentMap().getChunkNeighborsDiagonals(sourceChunk));
        if (entity.entityID == EntityID.Player.ID)
        {
            for (Chunk chunk : chunks)
            {
                for (Entity targetEntity : chunk.getEntities())
                {
                    if (damagedEntities.contains(targetEntity)) continue;   // do not damage same entity multiple times
                    if (targetEntity.getHitbox().getHitboxRect().intersects(attackHitbox.getHitboxRect()))    // if hitboxes intersects
                    {
                        damagedEntities.add(targetEntity);
                        entity.meleeDamageTarget(targetEntity, weapon);
                    }
                }
            }
        }
        else
        {
            Entity targetEntity = entity.gc.player;
            if (damagedEntities.contains(targetEntity)) return;
            if (targetEntity.getHitbox().getHitboxRect().intersects(attackHitbox.getHitboxRect()))    // if hitboxes intersects
            {
                damagedEntities.add(targetEntity);
                entity.meleeDamageTarget(targetEntity, weapon);
            }
        }
    }

    public void updateCurrentSprite()
    {
        if (entity.isMoving())
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
            entity.gc.updatables.remove(this);
            entity.getCurrentChunk().removeEntity(entity);
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

    private void moveTowardsDirection(Direction direction, int movementSpeed)
    {
        if (entity.isMoving())
        {
            int moveX = 0;
            int moveY = 0;
            // !Collisions.isColliding(entity) && !Collisions.isCollidingWithOtherHitbox(entity);

            if (!Collisions.willCollide(entity) && !Collisions.willCollideWithOtherHitbox(entity))
            {
                if (direction == Direction.UP)
                {
                    moveY -= movementSpeed;
                }
                else if (direction == Direction.DOWN)
                {
                    moveY += movementSpeed;
                }
                else if (direction == Direction.LEFT)
                {
                    moveX -= movementSpeed;
                }
                else if (direction == Direction.RIGHT)
                {
                    moveX += movementSpeed;
                }
            }

            int entityWidth = entity.getHitbox().getWidth();
            int entityHeight = entity.getHitbox().getHeight();
            Position entityPosition = entity.getWorldPosition();

            boolean canMoveLeft =
                    Collisions.isPositionUncollidable(new Position(entityPosition.x -movementSpeed, entityPosition.y)) && // up-left
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

            if (direction== Direction.UP_LEFT)
            {
                if (canMoveLeft) moveX -= movementSpeed / Math.sqrt(2);
                if (canMoveUp) moveY -= movementSpeed  / Math.sqrt(2);
            }
            if (direction == Direction.UP_RIGHT)
            {
                if (canMoveRight) moveX += movementSpeed  / Math.sqrt(2);
                if (canMoveUp) moveY -= movementSpeed  / Math.sqrt(2);
            }
            if (direction == Direction.DOWN_LEFT)
            {
                if (canMoveLeft) moveX -= movementSpeed  / Math.sqrt(2);
                if (canMoveDown) moveY += movementSpeed  / Math.sqrt(2);
            }
            if (direction == Direction.DOWN_RIGHT)
            {
                if (canMoveRight) moveX += movementSpeed  / Math.sqrt(2);
                if (canMoveDown) moveY += movementSpeed  / Math.sqrt(2);
            }

            entity.worldPosition.x += moveX;
            entity.worldPosition.y += moveY;
        }
    }

    private void moveTowardsDirection(Direction direction)
    {
        moveTowardsDirection(direction, entity.getMovementSpeed());
    }

    protected void moveTowardsDirection()
    {
        moveTowardsDirection(entity.direction);
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

                if (randomIsMoving == 0 || entity.isImmobilised) entity.setMoving(false);
                else entity.setMoving(true);
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
            entity.setMoving(false);
            return;
        }

        // Get the last point in the path
        Position target;
        if (path.length > 1) target = path[1];
        else return;
        if (entity.hitbox.isInsideHitbox(path[path.length-1]))
        {
            entity.setBehaviourState(BehaviourState.WANDER);
            entity.setPathToFollow(null);
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
                entity.setMoving(false);
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
        entity.setMoving(true);
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
            updatedMovementSpeed /= (float)(slot.getStoredItem().getStatistics().getMovementSpeedPenalty());
        }
        Item storedItem = entity.getCurrentBeltSlot().getStoredItem();
        if (storedItem != null) updatedMovementSpeed /= (float)(storedItem.getStatistics().getMovementSpeedPenalty());
        if (entity.isCrouching()) updatedMovementSpeed /= 3;
        entity.statistics.setCurrentMovementSpeed(updatedMovementSpeed);
    }

    /**
     * Applies a knockback effect to the entity, pushing it in the specified direction.
     * The strength of the knockback is determined by the percentage of the entity's
     * maximum health. if direction is null - no knockback will be applied
     *
     * @param receivedDamage The amount of damage received.
     * @param direction The direction in which the entity shall be pushed.
     */
    public void knockback(int receivedDamage, Direction direction)
    {
        if (entity == null || !entity.isAlive() || entity.isImmobilised || direction == null)
        {
            remainingKnockbackCounter = 0;
            knockbackStrength = 0;
            return;
        }
        if (remainingKnockbackCounter == 0) remainingKnockbackCounter = 5;

        knockbackDirection = direction;
        double damagePercentage = (double) receivedDamage / entity.getMaximumHealth();
        int scalingFactor = 230;

        knockbackStrength = Math.max(1, (int)(damagePercentage*1.2f * scalingFactor));   // minimal knockback strength value
        if (knockbackStrength > 18) knockbackStrength = 18;                             // maximal knockback strength value

    }

    private void updateKnockback()
    {
        if (remainingKnockbackCounter > 0 && knockbackDirection != null)
        {
            entity.setMoving(true);
            moveTowardsDirection(knockbackDirection, knockbackStrength);
            entity.setMoving(false);
            if (knockbackStrength >= 3) knockbackStrength -= 3; // how fast knockback fades away
            remainingKnockbackCounter--;

            if (remainingKnockbackCounter <= 0)
            {
                knockbackDirection = null;
            }
        }
    }
}
