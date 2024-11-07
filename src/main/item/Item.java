package main.item;

import main.Drawable;
import main.GameController;
import main.Hitbox;
import utilities.*;

import java.awt.*;

public class Item implements Drawable
{
    public GameController gc;
    public Sprite currentSprite;
    protected Position worldPosition;
    public Hitbox hitbox;
    public String name;

    public Position getWorldPosition() {return worldPosition;}

    public void setWorldPosition(Position worldPosition) {
        this.worldPosition = worldPosition;
    }

    public Item(GameController gc)
    {
        this.gc = gc;
        this.currentSprite = new SpriteSheet(FileManipulation.loadImage("resources/default/Item"), 48).extractFirst(); // default sprite
        //this.hitbox = new Hitbox(this);
        this.name = "default";
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Item.value;}

    @Override
    public void draw(Graphics g2)
    {
        Position screenPosition = gc.camera.applyCameraOffset(worldPosition.x, worldPosition.y);
        g2.drawImage(currentSprite.image, screenPosition.x, screenPosition.y, null);
    }
}
