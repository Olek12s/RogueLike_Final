package main.entity.player;

import main.Direction;
import main.GameController;
import main.entity.Entity;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import utilities.*;

public class Player extends Entity
{
    protected PlayerRenderer renderer;
    protected PlayerUpdater updater;

    public Player(GameController gc)
    {
        super(gc);
        renderer = setRenderer();
        updater = setUpdater();


        setMovementSpeed(80);


    }

    private PlayerRenderer setRenderer()
    {
        SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/defaultEntity"), 48);
        return new PlayerRenderer(this, spriteSheet);
    }

    private PlayerUpdater setUpdater()
    {
        return new PlayerUpdater(this);
    }
    @Override
    public void setCurrentSprite()
    {
        currentSprite = renderer.getSpriteSheet().extractFirst();
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
