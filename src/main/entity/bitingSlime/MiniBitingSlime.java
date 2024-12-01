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

import java.awt.*;



public class MiniBitingSlime extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22);
    final int attackPreparationTime = 60;     // time in x/60 seconds of how long entity prepares an attack
    final int attackSwingTime = 120;          // time in x/60 seconds of how long an attack hitbox exists
    final int attackRestTime = 300;           // time in x/60 seconds until next attack
    // move this shit to item stats ^
    public MiniBitingSlime(GameController gc, Position worldPosition)
    {
        super(gc, worldPosition, 1);
        this.worldPosition = worldPosition;
    }

    //@Override
    //public void setDefaultSprite()
    //{
    //    currentSprite = entityRenderer.getSpriteSheet().extractFirst();
    //}

    @Override
    public void setDefaultSprite()
    {
        currentSprite = EntityRenderer.getSpriteSheetByID(entityID).extractFirst();
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


    /**
     * Executes the primary and only type of attack for this entity, when hitting target is possible
     *
     *
     * @param target The entity that will be attacked by this entity
     */
    public void attack(Entity target)
    {
        int attackRange = 16;

        if (this.distanceBetween(target) <= attackRange)
        {
            System.out.println("ATTACK");
        }
    }
}
