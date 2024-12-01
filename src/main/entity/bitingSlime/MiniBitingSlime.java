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
        //findPath(target)

        if (target != null && this.distanceBetween(target) <= attackRange)     // if found target and within range
        {
            System.out.println("attack test");
            Graphics g = gc.getGraphics();

            Position attackerCenter = this.hitbox.getCenterPosition();
            Position targetCenter = target.getHitbox().getCenterPosition();

            int rectWidth = 1000;
            int rectHeight = 1000;
            int rectX = (attackerCenter.x + targetCenter.x) / 2 - rectWidth / 2;
            int rectY = (attackerCenter.y + targetCenter.y) / 2 - rectHeight / 2;


            Position screenPosition = gc.camera.applyCameraOffset(rectX, rectY);
            double scaleFactor = gc.camera.getScaleFactor();
            int scaledWidth = (int) (rectWidth * scaleFactor);
            int scaledHeight = (int) (rectHeight * scaleFactor);

            g.setColor(Color.RED);
            g.fillRect(screenPosition.x, screenPosition.y, scaledWidth, scaledHeight);
        }
    }
}
