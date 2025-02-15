package main.entity.player;

import main.controller.DrawPriorities;
import main.controller.Drawable;
import main.entity.Entity;
import main.entity.EntityRenderer;
import utilities.Hitbox;
import utilities.sprite.SpriteSheet;

import java.awt.*;

public class PlayerRenderer extends EntityRenderer implements Drawable
{
    Entity entity;

    public PlayerRenderer(Entity entity, SpriteSheet spriteSheet)
    {
        super(entity, spriteSheet);
        this.entity = entity;

    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Player.value;}

    @Override
    public void draw(Graphics g2)
    {
        super.draw(g2);

        //drawEntityHitbox(g2);
        //g2.dispose();
    }
}
