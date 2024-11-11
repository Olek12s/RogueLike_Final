package main.entity;

import main.DrawPriorities;
import main.Drawable;
import utilities.Position;
import utilities.SpriteSheet;

import java.awt.*;

public class EntityRenderer implements Drawable
{
    private Entity entity;
    protected SpriteSheet spriteSheet;

    public EntityRenderer(Entity entity, SpriteSheet spriteSheet)
    {
        this.entity = entity;
        this.spriteSheet = spriteSheet;
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Entity.value;}

    @Override
    public void draw(Graphics g2)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position screenPosition = entity.gc.camera.applyCameraOffset(entity.worldPosition.x, entity.worldPosition.y);

        int scaledWidth = (int) (entity.currentSprite.image.getWidth() * scaleFactor);
        int scaledHeight = (int) (entity.currentSprite.image.getHeight() * scaleFactor);


        g2.drawImage(entity.currentSprite.image, screenPosition.x, screenPosition.y, scaledWidth, scaledHeight, null);
    }
}
