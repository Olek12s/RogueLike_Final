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
    protected BufferedImage spriteSheet;
    protected Sprite currentSprite;
    protected ArrayList<ArrayList<Sprite>> spriteImages = new ArrayList<>();

    protected Direction direction;
    protected Position position;
    protected int speed;
    protected boolean isMoving;

    public Entity(GameController gc)
    {
        this.gc = gc;
        this.direction = Direction.DOWN;
        loadSpriteSheet("resources/default/SpriteSheet");
        this.currentSprite = new Sprite(SpriteSheet.extractFirst(spriteSheet, 48), 48);
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

    private void updateCurrentSprite()
    {
        int rowIndex;
        switch (direction)
        {
            case DOWN:      rowIndex = 0; break;
            case LEFT:      rowIndex = 1; break;
            case RIGHT:     rowIndex = 2; break;
            case UP:        rowIndex = 3; break;
            case UP_LEFT:   rowIndex = 4; break;
            case UP_RIGHT:  rowIndex = 5; break;
            case DOWN_LEFT: rowIndex = 6; break;
            case DOWN_RIGHT:rowIndex = 7; break;
            default:        rowIndex = 0;
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

    protected void loadSpriteSheet(String imagePath)
    {
        try
        {
            spriteSheet = ImageIO.read(new File(imagePath + ".png"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
