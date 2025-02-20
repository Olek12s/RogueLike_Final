package main.entity;

import main.DamageType;
import main.Direction;
import main.controller.GameController;
import main.entity.player.Player;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.item.Item;
import main.item.weapon.mobweapon.BareHands;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;
import world.map.Chunk;
import utilities.*;
import world.map.MapLevels;

import java.awt.*;
import java.util.Random;

public abstract class Entity
{
    public GameController gc;
    public EntityRenderer entityRenderer;
    public EntityUpdater entityUpdater;
    public int entityID;

    private Item itemHeldDuringAttack;
    private boolean isDuringMeleeAttack;

    protected Sprite currentSprite;
    protected Hitbox hitbox;
    protected int detectionDiameter;
    protected int loseInterestDiameter;
    protected Direction direction;
    protected Position worldPosition = new Position(0,0);
    private Chunk currentChunk;
    private boolean isMoving;
    protected String name = "";
    public boolean isImmobilised;
    private boolean isAlive;
    private MapLevels level;
    protected Inventory inventory;
    private int currentBeltSlotIndex;
    private Position[] pathToFollow;
    private BehaviourState behaviourState;
    private boolean isAlerted;
    protected boolean crouching;
    private final Item bareHands;
    private Hitbox attackHitbox;

    //STATISTICS
    public EntityStatistics statistics;
    public int getCurrentHealth() {return statistics.getHitPoints();}
    public int getMaximumHealth() {return statistics.getMaxHitPoints();}
    //STATISTICS

    public Entity(GameController gc, int entityID, Position worldPosition)
    {
        this.gc = gc;
        this.level = gc.mapController.getCurrentMap().getLevel();
        initializeEntitySpriteAssets(entityID);
        this.entityID = entityID;
        entityRenderer = setRenderer();
        entityUpdater = setUpdater();
        this.statistics = new EntityStatistics();
        this.isAlive = true;
        //this.currentSprite = entityRenderer.spriteSheet.extractFirst();
        this.currentSprite = EntityRenderer.getSpriteSheetByID(entityID).extractFirst();
        this.inventory = new Inventory(gc, this);
        this.currentBeltSlotIndex = 0;
        this.behaviourState = BehaviourState.WANDER;

        setDirection();
        setHitbox();
        setDetectionRadius();
        setLoseInterestRadius();
        this.worldPosition = gc.mapController.getCurrentMap().seekForNearestNonCollidableSpawnPosition(worldPosition, hitbox);
        entityUpdater.initUpdate();
        this.isMoving = false;
        bareHands = new BareHands(gc, new Position(0,0));
    }

    //ABSTRACTS
    public abstract void setDefaultSprite();
    public abstract void setHitbox();
    public abstract void setDirection();
    public abstract void setWorldPosition(Position worldPosition);
    public abstract EntityRenderer setRenderer();
    public abstract EntityUpdater setUpdater();
    public abstract void setupStatistics();
    public abstract void setDetectionRadius();
    public abstract void setLoseInterestRadius();
    //ABSTRACTS

    public Position getWorldPosition() {return worldPosition;}
    public Sprite getCurrentSprite() {return currentSprite;}
    public Direction getDirection() {return direction;}
    public void setDirection(Direction direction) {this.direction = direction;}
    public int getMovementSpeed() {return statistics.getCurrentMovementSpeed();}
    public Hitbox getHitbox() {return hitbox;}
    public Chunk getCurrentChunk() {return currentChunk;}
    public void setCurrentChunk(Chunk chunk) {this.currentChunk = chunk;}
    public int getID() {return entityID;}
    public int getMaxHitPoints() {return statistics.getMaxHitPoints();}
    public boolean isAlive() {return isAlive;}
    public void setAlive(boolean alive) {isAlive = alive;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public MapLevels getLevel() {return level;}
    public void setLevel(MapLevels level) {this.level = level;}
    public int getMaxMovementSpeed() {return statistics.getMaxMovementSpeed();}
    public Inventory getInventory() {return inventory;}
    public int getDetectionDiameter() {return detectionDiameter;}
    public int getLoseInterestDiameter() {return loseInterestDiameter;}
    public int getDetectionRadius() {return detectionDiameter/2;}
    public int getLoseInterestRadius() {return loseInterestDiameter/2;}
    public BehaviourState getBehaviourState() {return behaviourState;}
    public void setBehaviourState(BehaviourState behaviourState) {this.behaviourState = behaviourState;}
    public Position[] getPathToFollow() {return pathToFollow;}
    public void setPathToFollow(Position[] pathToFollow) {this.pathToFollow = pathToFollow;}
    public boolean isAlerted() {return isAlerted;}
    public void setAlerted(boolean alerted) {isAlerted = alerted;}
    public int getCurrentBeltSlotIndex() {return currentBeltSlotIndex;}
    public void setCurrentBeltSlotIndex(int currentBeltSlotIndex) {this.currentBeltSlotIndex = currentBeltSlotIndex;}
    public Slot getCurrentBeltSlot() {return inventory.getBeltSlots()[currentBeltSlotIndex];}
    public boolean isCrouching() {return crouching;}
    public void setCrouching(boolean crouching) {this.crouching = crouching;}
    public Item getBareHands() {return bareHands;}
    public Hitbox getAttackHitbox() {return attackHitbox;}
    public void setAttackHitbox(Hitbox hitbox) {this.attackHitbox = hitbox;}
    public boolean isDuringMeleeAttack() {return isDuringMeleeAttack;}
    public void setDuringMeleeAttack(boolean duringMeleeAttack) {isDuringMeleeAttack = duringMeleeAttack;}
    public Item getItemHeldDuringAttack() {return itemHeldDuringAttack;}
    public void setItemHeldDuringAttack(Item itemHeldDuringAttack) {this.itemHeldDuringAttack = itemHeldDuringAttack;}
    public boolean isMoving() {return isMoving;}
    public void setMoving(boolean moving) {isMoving = moving;}

    public void setDetectionDiameter(int r)
    {
        Random random = new Random();
        this.detectionDiameter = r;
        int randomOffset = (int) (r * (random.nextDouble() * 0.2 - 0.1)); // multiple (-0.1 to 0.1) randomly
        this.detectionDiameter = r + randomOffset;
    }

    public void setLoseInterestDiameter(int r)
    {
        Random random = new Random();
        int randomIncrease = (int) (r * (random.nextDouble() * 0.1)); // multiple (0 to 0.1) randomly
        this.loseInterestDiameter = r + randomIncrease;
    }

    public void initializeEntitySpriteAssets(int id)
    {
         EntityRenderer.putSpriteSheet(new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22), id);
         //EntityRenderer = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 22);
    }

    public double distanceBetween(Entity other)
    {
        Rectangle thisHitbox = this.getHitbox().getHitboxRect();
        Rectangle otherHitbox = other.getHitbox().getHitboxRect();

        if (thisHitbox.intersects(otherHitbox)) return 0;

        // calc shortest distance
        double dx = Math.max(0, Math.max(otherHitbox.x - (thisHitbox.x + thisHitbox.width), thisHitbox.x - (otherHitbox.x + otherHitbox.width)));
        double dy = Math.max(0, Math.max(otherHitbox.y - (thisHitbox.y + thisHitbox.height), thisHitbox.y - (otherHitbox.y + otherHitbox.height)));
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * this method calculates, how many damage should entity receive lowered by armor factor by formula:
     * X/(X+1.5*D)
     * WHere X is damage and D is armor
     * Knockback is applied according to the source Position. If source Position is null - no knocback will be applied
     *
     * @param damageInput   - base damage calculated by calculateDamageOutput()
     * @param type          - type of the damage (physical/magical/inevitable)
     */
    public void receiveDamage(int damageInput, DamageType type, Position sourcePosition)
    {
        //System.out.println("BASE DAMAGE: " + damageInput);
        double damageMultipler;
        switch (type)
        {
            case PHYSICAL:
                if (statistics.getArmour() != 0) damageMultipler = (double) damageInput / (damageInput + statistics.getArmour());
                else damageMultipler = 1;
                break;

            case MAGICAL:
                if (statistics.getMagicArmour() != 0) damageMultipler = (double) damageInput / (damageInput + statistics.getMagicArmour());
                else damageMultipler = 1;
                break;

            case INEVITABLE:
                damageMultipler = 1;  // Can't reduce this type of damage
                break;
            default:
                damageMultipler = 1;
        }
        int receivedDamage = (int) (damageMultipler*damageInput);
        if (receivedDamage <= 0) receivedDamage = 1;
        statistics.setHitPoints(statistics.getHitPoints() - receivedDamage);

        if (sourcePosition != null) // apply knockback away from the sourcePosition
        {
            Direction knockbackDirection = Position.getCounterDirection(hitbox.getCenterWorldPosition(), sourcePosition);
            entityUpdater.knockback(receivedDamage, knockbackDirection);
        }
    }

    /**
     *  Calculates damage output with currently held statistics and items
     * @param weapon    - currently held weapon
     * @return
     */
    public int calculateDamageOutput(Item weapon)
    {
        int output = 0;

        output += statistics.getStrength() + weapon.getDamageOutput(); // divided by Math.Max(0.2, (currentEnergy/maxEnergy) * 100));
        double randomMultiplier = 0.8 + (Math.random() * 0.4); // random between 0.8 to 1.2
        double criticalChance = 0.05;

        if (Math.random() < criticalChance) // Math.random returns between 0-1, that's why there's 5% chance for this condition to be true
        {
            randomMultiplier *= 1.5;
        }
        return (int) (output * randomMultiplier);
    }

    public void meleeDamageTarget(Entity target, Item weapon)
    {
        int damage = calculateDamageOutput(weapon);
        DamageType damageType = weapon.getDamageType();
        target.receiveDamage(damage, damageType, hitbox.getCenterWorldPosition());
    }

    /**
     * Modifies hitbox location based on entity's direction. If hitbox is null, creates new object.
     *
     */
    public void createAttackHitbox()
    {
        int attackWidth = itemHeldDuringAttack.getMeleeAttackWidth();
        int attackHeight = itemHeldDuringAttack.getMeleeAttackHeight();
        int halfAttackWidth = attackWidth / 2;
        int halfAttackHeight = attackHeight / 2;
        Position entityHitbox = this.hitbox.getWorldPosition();
        int entityHitboxXCenter = entityHitbox.x + (this.hitbox.getWidth() / 2);
        int entityHitboxYCenter = entityHitbox.y + (this.hitbox.getHeight() / 2);

        Position attackHitboxPos;
        int boxWidth = attackWidth;
        int boxHeight = attackHeight;
        int diagSize = (int) ((attackWidth + attackHeight) / Math.sqrt(2));
        switch (direction) {
            case UP: {
                attackHitboxPos = new Position(entityHitboxXCenter - halfAttackWidth, entityHitbox.y - attackHeight);
                break;
            }
            case DOWN: {
                attackHitboxPos = new Position(entityHitboxXCenter - halfAttackWidth, entityHitbox.y + this.hitbox.getHeight());
                break;
            }
            case LEFT: {
                boxWidth = attackHeight;
                boxHeight = attackWidth;
                attackHitboxPos = new Position(entityHitbox.x - boxWidth, entityHitboxYCenter - (boxHeight / 2));
                break;
            }
            case RIGHT: {
                boxWidth = attackHeight;
                boxHeight = attackWidth;
                attackHitboxPos = new Position(entityHitbox.x + this.hitbox.getWidth(), entityHitboxYCenter - (boxHeight / 2));
                break;
            }
            case UP_LEFT: {
                boxWidth = diagSize;
                boxHeight = diagSize;
                attackHitboxPos = new Position(entityHitbox.x - diagSize, entityHitbox.y - diagSize);
                break;
            }
            case UP_RIGHT: {
                boxWidth = diagSize;
                boxHeight = diagSize;
                attackHitboxPos = new Position(entityHitbox.x + this.hitbox.getWidth(), entityHitbox.y - diagSize);
                break;
            }
            case DOWN_LEFT: {
                boxWidth = diagSize;
                boxHeight = diagSize;
                attackHitboxPos = new Position(entityHitbox.x - diagSize, entityHitbox.y + this.hitbox.getHeight());
                break;
            }
            case DOWN_RIGHT: {
                boxWidth = diagSize;
                boxHeight = diagSize;
                attackHitboxPos = new Position(entityHitbox.x + this.hitbox.getWidth(), entityHitbox.y + this.hitbox.getHeight());
                break;
            }
            default:
                try {
                    throw new Exception("direction was not determined while creating attack hitbox");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        attackHitbox = new Hitbox(attackHitboxPos, boxWidth, boxHeight);
    }

    public void giveItemAtChance(Item item, float percentChance)
    {
        Random random = new Random();
        float chance = random.nextFloat() * 100;

        if (chance <= percentChance)
        {
            inventory.addItem(item);
        }
    }
}
