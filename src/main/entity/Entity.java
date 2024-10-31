package main.entity;


import main.Direction;
import main.Drawable;
import main.GameController;
import main.Updatable;
import utilities.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Entity implements Drawable, Updatable
{
    protected GameController gc;
    protected BufferedImage spriteSheet;
    protected BufferedImage currentImage;

    Direction direction;

    public Entity(GameController gc)
    {
        this.gc = gc;
    }



    abstract Position getPosition();
    abstract void setPosition(Position position);
    public void update()
    {
        updateDirection();
    }

    public int getMovementSpeed(int speed)
    {
        int movementSpeed = (int)(speed*2 / (gc.tileManager.tileSize));
        return Math.max(movementSpeed, 1);
    }

    protected void loadSpriteSheet(String imagePath, int textureResolution)
    {
        try
        {
            spriteSheet = ImageIO.read(new File(imagePath + ".png"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            try {
                spriteSheet = ImageIO.read(new File("resources/default/SpriteSheet.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
