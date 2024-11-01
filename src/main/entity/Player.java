package main.entity;

import main.Direction;
import main.GameController;
import main.Main;
import utilities.Position;

import java.awt.*;

public class Player extends Entity
{

    public Player(GameController gc)
    {
        super(gc);
        this.position = new Position(gc.getWidth()/2, gc.getHeight()/2);
        this.speed = 100;
        gc.drawables.add(this);
        gc.updatables.add(this);
        loadSpriteSheet("resources/player/PlayerSpriteSheet");
    }

    @Override
    public void update()
    {
        super.update();
        updatePlayerDirection();
        updatePosition(this);
        System.out.println("Player position: " + position.x + " " + position.y);
    }

    @Override
    public void draw(Graphics g2)
    {
        super.draw(g2);
    }

    private void updatePlayerDirection()
    {
        isMoving = false;
        if (gc.keyHandler.W_PRESSED && gc.keyHandler.A_PRESSED || gc.keyHandler.UP_PRESSED && gc.keyHandler.LEFT_PRESSED)     // Direction Up-Left
        {
            direction = Direction.UP_LEFT;
            isMoving = true;
        }
        else if (gc.keyHandler.W_PRESSED && gc.keyHandler.D_PRESSED || gc.keyHandler.UP_PRESSED && gc.keyHandler.RIGHT_PRESSED)     // Direction Up-Right
        {
            direction = Direction.UP_RIGHT;
            isMoving = true;
        }
        else if (gc.keyHandler.S_PRESSED && gc.keyHandler.A_PRESSED || gc.keyHandler.DOWN_PRESSED && gc.keyHandler.LEFT_PRESSED)     // Direction Down-Left
        {
            direction = Direction.DOWN_LEFT;
            isMoving = true;
        }
        else if (gc.keyHandler.S_PRESSED && gc.keyHandler.D_PRESSED || gc.keyHandler.DOWN_PRESSED && gc.keyHandler.RIGHT_PRESSED)     // Direction Down-Right
        {
            direction = Direction.DOWN_RIGHT;
            isMoving = true;
        }
        else if (gc.keyHandler.S_PRESSED || gc.keyHandler.DOWN_PRESSED)   // Direction Down
        {
            direction = Direction.DOWN;
            isMoving = true;
        }
        else if (gc.keyHandler.A_PRESSED || gc.keyHandler.LEFT_PRESSED)  // Direction Left
        {
            direction = Direction.LEFT;
            isMoving = true;
        }
        else if (gc.keyHandler.D_PRESSED || gc.keyHandler.RIGHT_PRESSED) // Direction right
        {
            direction = Direction.RIGHT;
            isMoving = true;
        }
        else if (gc.keyHandler.W_PRESSED || gc.keyHandler.UP_PRESSED)   // Direction up
        {
            direction = Direction.UP;
            isMoving = true;
        }
    }
}
