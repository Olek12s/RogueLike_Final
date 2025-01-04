package utilities;

import main.entity.Entity;

import java.awt.*;

public class Hitbox
{
    private Rectangle hitboxRect;
    private Position worldPosition;

    public Rectangle getHitboxRect() {return hitboxRect;}

    public Position getWorldPosition() {return worldPosition;}
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
    public Position getCenterPosition()
    {
        Position centerPosition = new Position(hitboxRect.x/2, hitboxRect.y/2);

        return centerPosition;
    }

    public Hitbox(Position worldPosition, int width, int height)
    {
        hitboxRect = new Rectangle(worldPosition.x, worldPosition.y, width, height);
        this.worldPosition = worldPosition;
    }

    public void centerPositionToEntity(Entity entity)
    {

        int entityX = entity.getWorldPosition().x;
        int entityY = entity.getWorldPosition().y;

        Sprite currentSprite = entity.getCurrentSprite();
        float spriteWidth = currentSprite.resolutionX;
        float spriteHeight = currentSprite.resolutionY;


        int spriteCenterX = entityX + (int)(spriteWidth / 2);
        int spriteCenterY = entityY + (int)(spriteHeight / 2);

        hitboxRect.x = spriteCenterX - (int)(hitboxRect.getWidth() / 2);
        hitboxRect.y = spriteCenterY - (int)(hitboxRect.getHeight() / 2);
    }


    @Override
    public String toString()
    {
        return "[" +  hitboxRect.getBounds().x + " " + hitboxRect.getBounds().y + "] [" + hitboxRect.getWidth() + ", " + hitboxRect.getHeight() + "]";
    }


}
