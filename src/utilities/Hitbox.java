package utilities;

import main.entity.Entity;

import java.awt.*;

public class Hitbox
{
    private Rectangle hitboxRect;
    private int width;
    private int height;

    public Rectangle getHitboxRect() {return hitboxRect;}
    public void setHitboxRect(Rectangle hitboxRect) {this.hitboxRect = hitboxRect;}
    public int getWidth() {return width;}
    public void setWidth(int width) {this.width = width;}
    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}

    public Hitbox(Position worldPosition, int width, int height)
    {
        hitboxRect = new Rectangle(worldPosition.x, worldPosition.y, width, height);
    }

    public void setNewPosition(Entity entity)
    {
        hitboxRect.x = entity.getWorldPosition().x;
        hitboxRect.y = entity.getWorldPosition().y;
        width = entity.getCurrentSprite().image.getWidth();
        height = entity.getCurrentSprite().image.getHeight();
    }


    @Override
    public String toString()
    {
        return "[" +  hitboxRect.getBounds().x + " " + hitboxRect.getBounds().y + "]";
    }
}
