package main.entity;

import main.DamageType;
import main.Direction;
import main.GameController;
import main.map.Chunk;
import utilities.*;

import java.awt.*;

public abstract class Entity
{
    public GameController gc;
    protected EntityRenderer entityRenderer;
    protected EntityUpdater entityUpdater;
    public int entityID;

    protected Sprite currentSprite;
    protected Hitbox hitbox;
    protected Direction direction;
    protected Position worldPosition;
    private Chunk currentChunk;
    public boolean isMoving;
    protected String name = "";

    //STATISTICS
    public Statistics statistics;
    //STATISTICS

    public Entity(GameController gc, Position worldPosition, int entityID)
    {
        this.gc = gc;
        initializeEntitySpriteAssets(entityID);
        this.entityID = entityID;
        entityRenderer = setRenderer();
        entityUpdater = setUpdater();
        this.statistics = new Statistics(this);


        //this.currentSprite = entityRenderer.spriteSheet.extractFirst();
        this.currentSprite = EntityRenderer.getSpriteSheetByID(entityID).extractFirst();
        setWorldPosition(worldPosition);
        setDirection();
        setHitbox();
        setMovementSpeed(1);
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
    //ABSTRACTS

    public Position getWorldPosition() {return worldPosition;}
    public Sprite getCurrentSprite() {return currentSprite;}
    public void setMovementSpeed(int speed)
    {
        if (speed == 0) statistics.movementSpeed = 0;
        else
        {
            statistics.movementSpeed = Math.max((int)(speed  / 16), 1);
        }
    }
    public Direction getDirection() {return direction;}
    public void setDirection(Direction direction) {this.direction = direction;}
    public int getMovementSpeed() {return statistics.movementSpeed;}
    public Hitbox getHitbox() {return hitbox;}
    public Chunk getCurrentChunk() {return currentChunk;}
    public void setCurrentChunk(Chunk chunk) {this.currentChunk = chunk;}
    public int getID() {return entityID;}
    public int getMaxHitPoints() {return statistics.maxHitPoints;}

    public void initializeEntitySpriteAssets(int id)
    {
         EntityRenderer.putSpriteSheet(new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22), id);
         //EntityRenderer = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 22);
    }

    public void receiveDamage(int amount, DamageType type)
    {
        switch (type)
        {
            case PHYSICAL:
                int receivedPhysical = amount - statistics.armour;
                statistics.hitPoints -= Math.max(1, receivedPhysical);
                break;

            case MAGICAL:
                int receivedMagical = amount - statistics.magicArmour;
                statistics.hitPoints -= Math.max(1, receivedMagical);
                break;

            case INEVITABLE:
                statistics.hitPoints -= Math.max(1, amount);
                break;
        }
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
