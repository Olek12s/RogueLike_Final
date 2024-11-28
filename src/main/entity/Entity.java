package main.entity;

import main.Direction;
import main.GameController;
import main.map.Chunk;
import utilities.*;

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

    public abstract EntityRenderer setRenderer();
    public abstract EntityUpdater setUpdater();

    public void initializeEntitySpriteAssets(int id)
    {
         EntityRenderer.putSpriteSheet(new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22), id);
         //EntityRenderer = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 22);
    }

}
