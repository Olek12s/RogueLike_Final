package main;

import main.entity.Entity;
import main.item.Item;

import java.awt.*;

public class Hitbox
{
    public Rectangle hitboxRect;
    public int width;
    public int height;

    public Hitbox(Entity entity)
    {
        this.hitboxRect = createDefaultHitbox(entity);
    }
    public Hitbox(Item item)
    {
        this.hitboxRect = createDefaultHitbox(item);
    }

    //By default - hitbox is a Rectangle centered over the entity's sprite resolution
    private Rectangle createDefaultHitbox(Entity entity)
    {
        int spriteWidth = entity.currentSprite.image.getWidth();
        int spriteHeight = entity.currentSprite.image.getHeight();

        return new Rectangle( entity.getWorldPosition().x,  entity.getWorldPosition().y, spriteWidth, spriteHeight);
    }

    //By default - hitbox is a Rectangle centered over the entity's sprite resolution
    private Rectangle createDefaultHitbox(Item item)
    {
        int spriteWidth = item.currentSprite.image.getWidth();
        int spriteHeight = item.currentSprite.image.getHeight();

        return new Rectangle( item.getWorldPosition().x,  item.getWorldPosition().y, spriteWidth, spriteHeight);
    }

    public void setNewPosition(Entity entity)
    {
        hitboxRect.x = entity.worldPosition.x;
        hitboxRect.y = entity.worldPosition.y;
        width = entity.currentSprite.image.getWidth();
        height = entity.currentSprite.image.getHeight();
    }


    @Override
    public String toString()
    {
        return "[" +  hitboxRect.getBounds().x + " " + hitboxRect.getBounds().y + "]";
    }
}
