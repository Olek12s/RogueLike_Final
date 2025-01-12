package main.entity;

import main.DamageType;
import main.Direction;
import main.GameController;
import main.item.Weapon;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;
import world.map.Chunk;
import utilities.*;
import world.map.MapLevels;

import java.awt.*;

public abstract class Entity
{
    public GameController gc;
    public EntityRenderer entityRenderer;
    public EntityUpdater entityUpdater;
    public int entityID;

    protected Sprite currentSprite;
    protected Hitbox hitbox;
    protected Direction direction;
    protected Position worldPosition = new Position(0,0);
    private Chunk currentChunk;
    public boolean isMoving;
    protected String name = "";
    public boolean isImmobilised;
    private boolean isAlive;
    private MapLevels level;

    //STATISTICS
    public EntityStatistics statistics;
    public int getCurrentHealth() {return statistics.hitPoints;}
    public int getMaximumHealth() {return statistics.maxHitPoints;}
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

        setDirection();
        setHitbox();
        this.worldPosition = gc.mapController.getCurrentMap().seekForNearestNonCollidableSpawnPosition(worldPosition, hitbox);
        entityUpdater.initUpdate();

        this.isMoving = false;
    }

    //ABSTRACTS
    public abstract void setDefaultSprite();
    public abstract void setHitbox();
    public abstract void setDirection();
    public abstract void setWorldPosition(Position worldPosition);
    public abstract EntityRenderer setRenderer();
    public abstract EntityUpdater setUpdater();
    public abstract void attack(Entity target);
    public abstract void setupStatistics();
    //ABSTRACTS

    public Position getWorldPosition() {return worldPosition;}
    public Sprite getCurrentSprite() {return currentSprite;}
    public Direction getDirection() {return direction;}
    public void setDirection(Direction direction) {this.direction = direction;}
    public int getMovementSpeed() {return statistics.movementSpeed;}
    public Hitbox getHitbox() {return hitbox;}
    public Chunk getCurrentChunk() {return currentChunk;}
    public void setCurrentChunk(Chunk chunk) {this.currentChunk = chunk;}
    public int getID() {return entityID;}
    public int getMaxHitPoints() {return statistics.maxHitPoints;}
    public boolean isAlive() {return isAlive;}
    public void setAlive(boolean alive) {isAlive = alive;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public MapLevels getLevel() {return level;}
    public void setLevel(MapLevels level) {this.level = level;}

    public void initializeEntitySpriteAssets(int id)
    {
         EntityRenderer.putSpriteSheet(new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22), id);
         //EntityRenderer = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 22);
    }

    /**
     * this method calculates, how many damage should entity receive lowered by armor factor by formula:
     * X/(X+1.5*D)
     * WHere X is damage and D is armor
     *
     * @param damageInput   - base damage calculated by calculateDamageOutput()
     * @param type          - type of the damage (physical/magical/inevitable)
     */
    public void receiveDamage(int damageInput, DamageType type)
    {
       //System.out.println("BASE DAMAGE: " + damageInput);
        double damageMultipler;
        switch (type)
        {
            case PHYSICAL:
                //int receivedPhysical = damageInput - statistics.armour;
                //statistics.hitPoints -= Math.max(1, receivedPhysical);
                if (statistics.armour != 0) damageMultipler = (double) damageInput / (damageInput + statistics.armour);
                else damageMultipler = 1;
                break;

            case MAGICAL:
                if (statistics.magicArmour != 0) damageMultipler = (double) damageInput / (damageInput + statistics.magicArmour);
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
        //System.out.println("RECEIVED DAMAGE: " + receivedDamage);
        statistics.hitPoints -= receivedDamage;

    }
    public int calculateDamageOutput(Weapon weapon)
    {
        int output = 0;

        output += statistics.strength + weapon.getDamageOutput(); // divided by Math.Max(0.2, (currentEnergy/maxEnergy) * 100));
        double randomMultiplier = 0.8 + (Math.random() * 0.4); // random between 0.8 to 1.2
        double criticalChance = 0.05;

        if (Math.random() < criticalChance) // Math.random returns between 0-1, that's why there's 5% chance for this condidiot to be true
        {
            randomMultiplier *= 1.5;    //
        }

        return (int) (output * randomMultiplier);
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
}
