package ui;

import main.DrawPriorities;
import main.Drawable;

import java.awt.*;

public class HUDRenderer implements Drawable
{
    HUD hud;

    public HUDRenderer(HUD hud)
    {
        this.hud = hud;
        hud.gc.drawables.add(this);
    }

    @Override
    public int getDrawPriority()
    {
        return DrawPriorities.HUD.value;
    }

    @Override
    public void draw(Graphics g2)
    {
        g2.drawImage(hud.heart.image, 0, 0, hud.scale, hud.scale, null);
    }
}
