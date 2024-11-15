package utilities;

import main.entity.Entity;

import java.awt.*;

public class Hitbox
{
    private Rectangle hitboxRect;

    public Rectangle getHitboxRect() {return hitboxRect;}
    public void setHitboxRect(Rectangle hitboxRect) {this.hitboxRect = hitboxRect;}
    public int getWidth() {return hitboxRect.width;}
    public void setWidth(int width)
    {
        this.hitboxRect.setSize(width, hitboxRect.height);
    }
    public int getHeight() {return hitboxRect.height;}
    public void setHeight(int height)
    {
        this.hitboxRect.setSize(hitboxRect.width, height);
    }

    public Hitbox(Position worldPosition, int width, int height)
    {
        hitboxRect = new Rectangle(worldPosition.x, worldPosition.y, width, height);
    }

    public void centerPositionToEntity(Entity entity)
    {
        int entityX = entity.getWorldPosition().x;
        int entityY = entity.getWorldPosition().y;

        Sprite currentSprite = entity.getCurrentSprite();
        float spriteWidth = currentSprite.resolutionX;
        float spriteHeight = currentSprite.resolutionY;

        hitboxRect.x = (int)(entityX + (spriteWidth / 2) - (hitboxRect.width / 2));
        hitboxRect.y = (int)(entityY + (spriteHeight / 2) - (hitboxRect.height / 2));
    }


    @Override
    public String toString()
    {
        return "[" +  hitboxRect.getBounds().x + " " + hitboxRect.getBounds().y + "] [" + hitboxRect.getWidth() + ", " + hitboxRect.getHeight() + "]";
    }
}
