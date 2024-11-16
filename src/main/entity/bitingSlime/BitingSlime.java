package main.entity.bitingSlime;

import main.Direction;
import main.GameController;
import main.entity.Entity;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import utilities.Hitbox;
import utilities.Position;

public class BitingSlime extends Entity
{

    public BitingSlime(GameController gc, Position worldPosition)
    {
        super(gc, worldPosition);
        this.worldPosition = worldPosition;
    }

    @Override
    public void setDefaultSprite()
    {
        currentSprite = renderer.getSpriteSheet().extractFirst();
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(new Position(worldPosition.x, worldPosition.y), (int)(currentSprite.resolutionX*1), (int)(currentSprite.resolutionY*1));
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

    @Override
    public EntityRenderer setRenderer()
    {
        return renderer;
    }

    @Override
    public EntityUpdater setUpdater()
    {
        return updater;
    }
}
