package ui;

import main.DrawPriorities;
import main.Drawable;
import org.w3c.dom.css.RGBColor;

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
        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));

        if (hud.gc.player.hitPoints < 0) hud.gc.player.hitPoints = 0;
        int healthPercent = (int) ((hud.gc.player.hitPoints / (double) hud.gc.player.getMaxHitPoints()) * 100);

        g2d.drawRect(45, 24, 300, 26);
        g2d.setColor(new Color(75, 61, 61, 210));
        g2d.fillRect(46, 25, 299, 25);
        g2d.setColor(Color.RED);
        g2d.fillRect(46, 25, (healthPercent*299)/100, 25);
        g2d.drawImage(hud.heart.image, 0, 0, hud.scale, hud.scale, null);
    }
}
