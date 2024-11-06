package main;

import main.entity.Entity;
import main.item.Item;

import java.awt.*;

public class Hitbox
{
    public Polygon hitbox;

    public Hitbox(Entity entity)
    {
        this.hitbox = createDefaultHitbox(entity);
    }
    public Hitbox(Item item)
    {
        this.hitbox = createDefaultHitbox(item);
    }

    //By default - hitbox is a Rectangle centered over the entity's sprite lessen by 20%
    private Polygon createDefaultHitbox(Entity entity)
    {
        int spriteWidth = entity.currentSprite.image.getWidth();
        int spriteHeight = entity.currentSprite.image.getHeight();
        int hitboxWidth = (int)(spriteWidth * 0.8);
        int hitboxHeight = (int)(spriteHeight * 0.8);
        int centerX = entity.getWorldPosition().x + spriteWidth / 2;
        int centerY = entity.getWorldPosition().y + spriteHeight / 2;
        int hitboxX = centerX - hitboxWidth / 2;
        int hitboxY = centerY - hitboxHeight / 2;

        return new Polygon(
                new int[] {hitboxX, hitboxX + hitboxWidth, hitboxX + hitboxWidth, hitboxX},
                new int[] {hitboxY, hitboxY, hitboxY + hitboxHeight, hitboxY + hitboxHeight},
                4
        );
    }

    //By default - hitbox is a Rectangle centered over the entity's sprite lessen by 30%
    private Polygon createDefaultHitbox(Item item)
    {
        int spriteWidth = item.currentSprite.image.getWidth();
        int spriteHeight = item.currentSprite.image.getHeight();
        int hitboxWidth = (int)(spriteWidth * 0.7);
        int hitboxHeight = (int)(spriteHeight * 0.7);
        int centerX = item.getWorldPosition().x + spriteWidth / 2;
        int centerY = item.getWorldPosition().y + spriteHeight / 2;
        int hitboxX = centerX - hitboxWidth / 2;
        int hitboxY = centerY - hitboxHeight / 2;

        return new Polygon(
                new int[] {hitboxX, hitboxX + hitboxWidth, hitboxX + hitboxWidth, hitboxX},
                new int[] {hitboxY, hitboxY, hitboxY + hitboxHeight, hitboxY + hitboxHeight},
                4
        );
    }
}
