package main.entity;

import main.DrawPriorities;
import main.Drawable;
import utilities.Position;
import utilities.Sprite;
import utilities.SpriteSheet;

import java.awt.*;

public class EntityRenderer implements Drawable
{
    private Entity entity;
    protected SpriteSheet spriteSheet;
    public Sprite[][] spriteImages;

    public Entity getEntity() {return entity;}
    public SpriteSheet getSpriteSheet() {return spriteSheet;}

    public EntityRenderer(Entity entity, SpriteSheet spriteSheet)
    {
        this.entity = entity;
        this.spriteSheet = spriteSheet;
        loadSpriteImages();

        //entity.gc.drawables.add(this);
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


        g2.drawImage(entity.currentSprite.image, entity.getWorldPosition().x, entity.getWorldPosition().y, scaledWidth, scaledHeight, null);
    }

    protected void loadSpriteImages()
    {
        int ticks = spriteSheet.countAnimationTicks();
        int variations = spriteSheet.countSpriteVariations();
        spriteImages = new Sprite[ticks][variations];

        for (int tick = 0; tick < ticks; tick++)
        {
            for (int variation = 0; variation < variations; variation++)
            {
                spriteImages[tick][variation] = spriteSheet.extractSprite(spriteSheet, tick, variation);
            }
        }
    }
}
