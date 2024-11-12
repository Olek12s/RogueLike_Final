package main.entity.player;

import main.Drawable;
import main.entity.Entity;
import main.entity.EntityRenderer;
import utilities.Position;
import utilities.SpriteSheet;

import java.awt.*;

public class PlayerRenderer extends EntityRenderer implements Drawable
{

    public PlayerRenderer(Entity entity, SpriteSheet spriteSheet)
    {
        super(entity, spriteSheet);

        entity.gc.drawables.add(this);
    }

    @Override
    public void draw(Graphics g2)
    {
        super.draw(g2);
    }
}
