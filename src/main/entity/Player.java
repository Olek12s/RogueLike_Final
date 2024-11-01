package main.entity;

import main.GameController;
import main.Main;
import utilities.Position;

import java.awt.*;

public class Player extends Entity
{
    private int speed = 100;

    public Player(GameController gc)
    {
        super(gc);
        this.position = new Position(gc.getWidth()/2, gc.getHeight()/2);
        gc.drawables.add(this);
        gc.updatables.add(this);
        loadSpriteSheet("resources/player/PlayerSpriteSheet");
    }

    @Override
    public void update()
    {
        super.update();
        updateDirection();
        System.out.println("Player position: " + position.x + " " + position.y);
    }

    private void updateDirection()
    {
        if (gc.keyHandler.W_PRESSED && gc.keyHandler.A_PRESSED || gc.keyHandler.UP_PRESSED && gc.keyHandler.LEFT_PRESSED)     // Direction Up-Left
        {
            position.x -= (int)(getMovementSpeed(speed) / Math.sqrt(2));
            position.y -= (int)(getMovementSpeed(speed) / Math.sqrt(2));
        }
        else if (gc.keyHandler.W_PRESSED && gc.keyHandler.D_PRESSED || gc.keyHandler.UP_PRESSED && gc.keyHandler.RIGHT_PRESSED)     // Direction Up-Right
        {
            position.x += (int)(getMovementSpeed(speed) / Math.sqrt(2));
            position.y -= (int)(getMovementSpeed(speed) / Math.sqrt(2));
        }
        else if (gc.keyHandler.S_PRESSED && gc.keyHandler.A_PRESSED || gc.keyHandler.DOWN_PRESSED && gc.keyHandler.LEFT_PRESSED)     // Direction Down-Left
        {
            position.x -= (int)(getMovementSpeed(speed) / Math.sqrt(2));
            position.y += (int)(getMovementSpeed(speed) / Math.sqrt(2));
        }
        else if (gc.keyHandler.S_PRESSED && gc.keyHandler.D_PRESSED || gc.keyHandler.DOWN_PRESSED && gc.keyHandler.RIGHT_PRESSED)     // Direction Down-Right
        {
            position.x += (int)(getMovementSpeed(speed) / Math.sqrt(2));
            position.y += (int)(getMovementSpeed(speed) / Math.sqrt(2));
        }
        else if (gc.keyHandler.S_PRESSED || gc.keyHandler.DOWN_PRESSED) position.y += getMovementSpeed(speed);   // Direction Down
        else if (gc.keyHandler.A_PRESSED || gc.keyHandler.LEFT_PRESSED) position.x -= getMovementSpeed(speed);   // Direction Left
        else if (gc.keyHandler.D_PRESSED || gc.keyHandler.RIGHT_PRESSED) position.x += getMovementSpeed(speed);  // Direction right
        else if (gc.keyHandler.W_PRESSED || gc.keyHandler.UP_PRESSED) position.y -= getMovementSpeed(speed);     // Direction Up
    }

    @Override
    public void draw(Graphics g2)
    {
        super.draw(g2);
        g2.setColor(Color.BLUE);
        g2.fillRect(position.x, position.y, 30, 30);
    }
}
