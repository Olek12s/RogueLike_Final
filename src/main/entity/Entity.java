package main.entity;


import main.*;
import utilities.*;

import java.awt.*;

public class Entity implements Drawable, Updatable
{
    public GameController gc;
    protected SpriteSheet spriteSheet;
    public Sprite currentSprite;
    protected Sprite[][] spriteImages;
    public Hitbox hitbox;
    public Direction direction;
    public Position worldPosition;
    protected int movementSpeed;
    public boolean isMoving;
    public String name;

    private int spriteCounter = 0;
    protected int animationSpeed = 6;



    public Entity(GameController gc, Position position)
    {
        this.gc = gc;
        this.direction = Direction.DOWN;
        this.spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/SpriteSheet"), 48);
        this.currentSprite = spriteSheet.extractFirst();
        this.worldPosition = position;
        this.hitbox = new Hitbox(this); // default hitbox
        this.name = "default entity name";
        //setMovementSpeed(100);
        loadSpriteImages();
    }

    //GETTERS AND SETTERS

    public Position getWorldPosition() {
        return worldPosition;
    }
    public void setWorldPosition(Position position) {
        this.worldPosition = position;
    }
    public int getMovementSpeed() {return movementSpeed;}
    public void setMovementSpeed(int speed)
    {
        System.out.println("movement speed set");
        movementSpeed = Math.max((int)(speed *2 / 32), 1);
    }

    private void drawHitbox(Graphics g2)
    {
        Position screenPosition = gc.camera.applyCameraOffset(worldPosition.x, worldPosition.y);
        g2.setColor(Color.ORANGE);
        g2.drawRect(screenPosition.x, screenPosition.y, hitbox.width, hitbox.height);
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Entity.value;}

    @Override
    public void draw(Graphics g2)
    {
        Position screenPosition = gc.camera.applyCameraOffset(worldPosition.x, worldPosition.y);
        g2.drawImage(currentSprite.image, screenPosition.x, screenPosition.y, null);
        drawHitbox(g2);
        //g2.dispose();
    }

    @Override
    public void update()
    {
        updateCurrentSprite();
        move();
        updateHitbox();
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

    private void updateHitbox()
    {
        hitbox.setNewPosition(this);
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

    protected void move()
    {
        if (isMoving && !Collisions.isColliding(this))
        {
            if (direction == Direction.UP_LEFT)
            {
                worldPosition.x -= Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
                worldPosition.y -= Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.UP_RIGHT)
            {
                worldPosition.x += Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
                worldPosition.y -= Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.DOWN_LEFT)
            {
                worldPosition.x -= Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
                worldPosition.y += Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.DOWN_RIGHT)
            {
                worldPosition.x += Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
                worldPosition.y += Math.max((int)(getMovementSpeed() / Math.sqrt(2)), 1);
            }
            else if (direction == Direction.DOWN)
            {
                worldPosition.y += getMovementSpeed();
            }
            else if (direction == Direction.LEFT)
            {
                worldPosition.x -= getMovementSpeed();
            }
            else if (direction == Direction.RIGHT)
            {
                worldPosition.x += getMovementSpeed();
            }
            else if (direction == Direction.UP)
            {
                worldPosition.y -= getMovementSpeed();
            }
        }
    }
}