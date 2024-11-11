package main.entity;

import main.Direction;
import main.GameController;
import utilities.*;

import javax.swing.*;

public abstract class Entity
{
    public GameController gc;
    protected EntityRenderer renderer = setRenderer();
    protected EntityUpdater updater = setUpdater();

    protected Sprite currentSprite;
    protected Hitbox hitbox;
    protected Direction direction;
    protected Position worldPosition;
    public boolean isMoving;


    //STATISTICS
    protected String name = "";
    protected int maxHitPoints = 1;
    protected int hitPoints = maxHitPoints;
    protected int movementSpeed = 5;
    //STATISTICS

    public Entity(GameController gc)
    {
        this.gc = gc;
        this.currentSprite = renderer.spriteSheet.extractFirst();
        setHitbox();
        setDirection();
        setWorldPosition();
        this.isMoving = false;

        gc.updatables.add(this.updater);
        gc.drawables.add(this.renderer);
    }

    //ABSTRACTS
    public abstract void setCurrentSprite();
    public abstract void setHitbox();
    public abstract void setDirection();
    public abstract void setWorldPosition();
    //ABSTRACTS

    public Position getWorldPosition() {return worldPosition;}
    public Sprite getCurrentSprite() {return currentSprite;}
    public void setMovementSpeed(int speed)
    {
        movementSpeed = Math.max((int)(speed *2 / 32), 1);
    }

    private EntityRenderer setRenderer()
    {
        SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/SpriteSheet"), 48);
        return new EntityRenderer(this, spriteSheet);
    }

    private EntityUpdater setUpdater()
    {
        return new EntityUpdater(this);
    }
}
