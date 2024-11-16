package main.entity.bitingSlime;

import main.Direction;
import main.GameController;
import main.entity.Entity;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import utilities.FileManipulation;
import utilities.Hitbox;
import utilities.Position;
import utilities.SpriteSheet;

public class MiniBitingSlime extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime"), 48);

    public MiniBitingSlime(GameController gc, Position worldPosition, SpriteSheet spriteSheet)
    {
        super(gc, worldPosition);
        this.worldPosition = worldPosition;
    }

    @Override
    public void setDefaultSprite()
    {
        currentSprite = entityRenderer.getSpriteSheet().extractFirst();
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(new Position(worldPosition.x, worldPosition.y), (int)(currentSprite.resolutionX*0.4), (int)(currentSprite.resolutionY*0.4));
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
        return new EntityRenderer(this, spriteSheet);
    }

    @Override
    public EntityUpdater setUpdater()
    {
        return new EntityUpdater(this);
    }
}
