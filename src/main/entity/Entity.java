package main.entity;


import main.*;
import utilities.*;

import java.awt.*;

public class Entity implements Drawable, Updatable
{
    protected GameController gc;
    protected SpriteSheet spriteSheet;
    public Sprite currentSprite;
    protected Sprite[][] spriteImages;
    public Hitbox hitbox;
    protected Direction direction;
    protected Position position;
    protected int speed;
    protected boolean isMoving;
    public String name;

    private int spriteCounter = 0;
    protected int animationSpeed = 6;



    public Entity(GameController gc)
    {
        this.gc = gc;
        this.direction = Direction.DOWN;
        this.spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/SpriteSheet"), 48);
        this.currentSprite = spriteSheet.extractFirst();
        this.name = "default entity name";
        //this.hitbox = new Hitbox(this);
        loadSpriteImages();
    }

    //GETTERS AND SETTERS

    public Position getWorldPosition() {
        return position;
    }
    public void setWorldPosition(Position position) {
        this.position = position;
    }
    public int getSpeed() {return speed;}
    public void setSpeed(int speed) {this.speed = speed;}

    @Override
    public int getDrawPriority() {return DrawPriorities.Entity.value;}

    @Override
    public void draw(Graphics g2)
    {
        Position screenPosition = gc.camera.applyCameraOffset(position.x, position.y);
        g2.drawImage(currentSprite.image, screenPosition.x, screenPosition.y, null);


        //g2.dispose();
    }

    @Override
    public void update()
    {
        updateCurrentSprite();
    }

    private void updateCurrentSprite()
    {
        if (isMoving)
        {
            spriteCounter = (spriteCounter + 1) % (spriteImages.length * animationSpeed);
            int currentAnimationTick = (spriteCounter / animationSpeed);
            if (currentAnimationTick == 0) currentAnimationTick = 1;
            changeSprite(direction, currentAnimationTick);
        }
        else
        {
            spriteCounter = 0;
            changeSprite(direction, 0);
        }
    }

    protected void changeSprite(Direction direction, int animationTick)
    {
        switch (direction)
        {
            case DOWN:      currentSprite = spriteImages[animationTick][0]; break;
            case LEFT:      currentSprite = spriteImages[animationTick][1]; break;
            case RIGHT:     currentSprite = spriteImages[animationTick][2]; break;
            case UP:        currentSprite = spriteImages[animationTick][3]; break;
            case UP_LEFT:   currentSprite = spriteImages[animationTick][4]; break;
            case UP_RIGHT:  currentSprite = spriteImages[animationTick][5]; break;
            case DOWN_LEFT: currentSprite = spriteImages[animationTick][6]; break;
            case DOWN_RIGHT:currentSprite = spriteImages[animationTick][7]; break;
            default:        currentSprite = spriteImages[0][0]; break;
        }
    }

    protected void loadSpriteImages()
    {
        int ticks = spriteSheet.countAnimationTicks();
        int variations = spriteSheet.countSpriteVariations();
        spriteImages = new Sprite[ticks][variations];

        for (int tick = 0; tick < ticks; tick++)
        {
            for (int variation = 0; variation < variations; variation++)
            {
                spriteImages[tick][variation] = spriteSheet.extractSprite(spriteSheet, tick, variation);
            }
        }
    }

    protected void updatePosition()
    {
        if (isMoving && !Collisions.isColliding(this))
        {
            if (direction == Direction.UP_LEFT)
            {
                position.x -= Math.max((int)(getMovementSpeed(this.speed) / Math.sqrt(2)), 1);
                position.y -= Math.max((int)(getMovementSpeed(this.speed) / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.UP_RIGHT)
            {
                position.x += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                position.y -= Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.DOWN_LEFT)
            {
                position.x -= Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                position.y += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.DOWN_RIGHT)
            {
                position.x += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
                position.y += Math.max((int)(getMovementSpeed(speed) / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.DOWN)
            {
                position.y += getMovementSpeed(speed);
            }
            else if (direction == Direction.LEFT)
            {
                position.x -= getMovementSpeed(speed);
            }
            else if (direction == Direction.RIGHT)
            {
                position.x += getMovementSpeed(speed);
            }
            else if (direction == Direction.UP)
            {
                position.y -= getMovementSpeed(speed);
            }
        }
    }

    public int getMovementSpeed(int speed)
    {
        int movementSpeed = (int)(speed*2 / 32);
        return Math.max(movementSpeed, 1);
    }
}
