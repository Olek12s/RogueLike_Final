package main.entity;


import main.Direction;
import main.Drawable;
import main.GameController;
import main.Updatable;
import utilities.Position;
import utilities.Sprite;
import utilities.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Entity implements Drawable, Updatable
{
    protected GameController gc;
    protected SpriteSheet spriteSheet;
    protected Sprite currentSprite;
    protected Sprite[][] spriteImages;

    protected Direction direction;
    protected Position position;
    protected int speed;
    protected boolean isMoving;

    public Entity(GameController gc)
    {
        this.gc = gc;
        this.direction = Direction.DOWN;
        this.spriteSheet = new SpriteSheet(loadSpriteSheet("resources/default/SpriteSheet"), 48);
        this.currentSprite = spriteSheet.extractFirst(spriteSheet);
        loadSpriteImages();
    }

    //GETTERS AND SETTERS

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public int getSpeed() {return speed;}
    public void setSpeed(int speed) {this.speed = speed;}

    @Override
    public void draw(Graphics g2)
    {
        g2.drawImage(currentSprite.image, position.x, position.y, null);


        g2.dispose();
    }

    @Override
    public void update()
    {
        updateCurrentSprite();
    }

    protected int animationTick = 0;
    protected int animationSpeed = 12;

    private void updateCurrentSprite()
    {
        if (isMoving)
        {
            animationTick = (animationTick + 1) % (spriteImages.length * animationSpeed);

            int currentFrame = (animationTick / animationSpeed) % spriteImages.length;

            switch (direction)
            {
                case DOWN:      currentSprite = spriteImages[currentFrame][0]; break;
                case LEFT:      currentSprite = spriteImages[currentFrame][1]; break;
                case RIGHT:     currentSprite = spriteImages[currentFrame][2]; break;
                case UP:        currentSprite = spriteImages[currentFrame][3]; break;
                case UP_LEFT:   currentSprite = spriteImages[currentFrame][4]; break;
                case UP_RIGHT:  currentSprite = spriteImages[currentFrame][5]; break;
                case DOWN_LEFT: currentSprite = spriteImages[currentFrame][6]; break;
                case DOWN_RIGHT:currentSprite = spriteImages[currentFrame][7]; break;
                default:        currentSprite = spriteImages[0][0]; break;
            }
        }
        else
        {
            switch (direction) {
                case DOWN:      currentSprite = spriteImages[0][0]; break;
                case LEFT:      currentSprite = spriteImages[0][1]; break;
                case RIGHT:     currentSprite = spriteImages[0][2]; break;
                case UP:        currentSprite = spriteImages[0][3]; break;
                case UP_LEFT:   currentSprite = spriteImages[0][4]; break;
                case UP_RIGHT:  currentSprite = spriteImages[0][5]; break;
                case DOWN_LEFT: currentSprite = spriteImages[0][6]; break;
                case DOWN_RIGHT:currentSprite = spriteImages[0][7]; break;
                default:        currentSprite = spriteImages[0][0]; break;
            }
        }
    }

    protected void loadSpriteImages()
    {
        int ticks = spriteSheet.countAnimationTicks(spriteSheet, 48);
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

    protected void updatePosition(Entity entityy)
    {
        if (isMoving)
        {
            if (direction == Direction.UP_LEFT)
            {
                position.x -= Math.max((int)(getMovementSpeed(entityy.speed) / Math.sqrt(2)), 1);
                position.y -= Math.max((int)(getMovementSpeed(entityy.speed) / Math.sqrt(2)), 1);
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
        int movementSpeed = (int)(speed*2 / (gc.tileManager.tileSize));
        return Math.max(movementSpeed, 1);
    }

    protected BufferedImage loadSpriteSheet(String imagePath)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(imagePath + ".png"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return image;
    }
}
