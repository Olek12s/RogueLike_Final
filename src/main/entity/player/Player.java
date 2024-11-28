package main.entity.player;

import main.Direction;
import main.GameController;
import main.entity.Entity;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import utilities.*;

public class Player extends Entity
{
    private static Position startingPosition = new Position(-5632+500,-5632+680);


    public Player(GameController gc)
    {
        super(gc, startingPosition, 0); // player's entityID is 0!
        setMovementSpeed(70);
        statistics.maxHitPoints = 500;
        statistics.hitPoints = statistics.maxHitPoints;
    }

    @Override
    public PlayerRenderer setRenderer()
    {
        SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22);
        return new PlayerRenderer(this, spriteSheet);
    }

    @Override
    public PlayerUpdater setUpdater()
    {
        return new PlayerUpdater(this);
    }
    @Override
    public void setDefaultSprite()
    {
        //currentSprite = renderer.getSpriteSheet().extractFirst();
        currentSprite = PlayerRenderer.getSpriteSheetByID(entityID).extractFirst();
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, (int)(currentSprite.resolutionX), (int)(currentSprite.resolutionY));
    }

    @Override
    public void setDirection()
    {
        direction = Direction.DOWN;
    }

    @Override
    public void setWorldPosition(Position worldPosition)
    {
        this.worldPosition = worldPosition;
    }
}
