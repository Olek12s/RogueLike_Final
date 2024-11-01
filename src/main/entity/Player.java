package main.entity;

import main.GameController;
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
        if (gc.keyHandler.W_PRESSED || gc.keyHandler.UP_PRESSED) position.y -= getMovementSpeed(speed);
        if (gc.keyHandler.S_PRESSED || gc.keyHandler.DOWN_PRESSED) position.y += getMovementSpeed(speed);
        if (gc.keyHandler.A_PRESSED || gc.keyHandler.LEFT_PRESSED) position.x -= getMovementSpeed(speed);
        if (gc.keyHandler.D_PRESSED || gc.keyHandler.RIGHT_PRESSED) position.x += getMovementSpeed(speed);
    }

    @Override
    public void draw(Graphics g2)
    {
        super.draw(g2);
        g2.setColor(Color.BLUE);
        g2.fillRect(position.x, position.y, 30, 30);
    }
}
