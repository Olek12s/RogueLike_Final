package main.entity;

import main.Direction;
import main.GameController;
import utilities.*;

import javax.swing.*;

public abstract class Entity
{
    public GameController gc;
    protected EntityRenderer renderer;
    protected EntityUpdater updater;

    protected Sprite currentSprite;
    protected Hitbox hitbox;
    protected Direction direction;
    protected Position worldPosition;
    public boolean isMoving;


    //STATISTICS
    protected String name = "";
    protected int maxHitPoints = 1;
    protected int hitPoints = maxHitPoints;
    protected int movementSpeed;
    //STATISTICS

    public Entity(GameController gc)
    {
        this.gc = gc;
        renderer = setRenderer();
        updater = setUpdater();


        this.currentSprite = renderer.spriteSheet.extractFirst();
        setWorldPosition();
        setDirection();
        setHitbox();
        setMovementSpeed(10);
        this.isMoving = false;

    }

    //ABSTRACTS
    public abstract void setDefaultSprite();
    public abstract void setHitbox();
    public abstract void setDirection();
    public abstract void setWorldPosition();
    //ABSTRACTS

    public Position getWorldPosition() {return worldPosition;}
    public Sprite getCurrentSprite() {return currentSprite;}
    public void setMovementSpeed(int speed)
    {
        if (speed == 0) movementSpeed = 0;
        else
        {
            movementSpeed = Math.max((int)(speed  / 128), 1);
        }
    }
    public Direction getDirection() {return direction;}
    public void setDirection(Direction direction) {this.direction = direction;}
    public int getMovementSpeed() {return movementSpeed;}

    public abstract EntityRenderer setRenderer();
    public abstract EntityUpdater setUpdater();

}
