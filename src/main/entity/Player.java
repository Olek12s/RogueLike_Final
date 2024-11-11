package main.entity;

import main.Direction;
import main.GameController;
import utilities.Hitbox;
import utilities.Position;
import utilities.Sprite;

public class Player extends Entity
{

    public Player(GameController gc)
    {
        super(gc);
        setMovementSpeed(80);
    }

    @Override
    public void setCurrentSprite()
    {
        currentSprite = renderer.spriteSheet.extractFirst();
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, currentSprite.resolutionX, currentSprite.resolutionY);
    }

    @Override
    public void setDirection()
    {
        direction = Direction.DOWN;
    }

    @Override
    public void setWorldPosition()
    {
        worldPosition = new Position(0, 0);
    }
}
