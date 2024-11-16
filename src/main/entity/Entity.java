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

    protected Sprite currentSprite;
    protected Hitbox hitbox;
    protected Direction direction;
    protected Position worldPosition;
    private Chunk currentChunk;
    public boolean isMoving;


    //STATISTICS
    protected String name = "";
    protected int maxHitPoints = 1;
    protected int hitPoints = maxHitPoints;
    protected int movementSpeed;
    //STATISTICS

    public Entity(GameController gc, Position worldPosition)
    {
        this.gc = gc;
        entityRenderer = setRenderer();
        entityUpdater = setUpdater();


        this.currentSprite = entityRenderer.spriteSheet.extractFirst();
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
        if (speed == 0) movementSpeed = 0;
        else
        {
            movementSpeed = Math.max((int)(speed  / 16), 1);
        }
    }
    public Direction getDirection() {return direction;}
    public void setDirection(Direction direction) {this.direction = direction;}
    public int getMovementSpeed() {return movementSpeed;}
    public Hitbox getHitbox() {return hitbox;}
    public Chunk getCurrentChunk() {return currentChunk;}
    public void setCurrentChunk(Chunk chunk) {this.currentChunk = chunk;}

    public abstract EntityRenderer setRenderer();
    public abstract EntityUpdater setUpdater();

}
