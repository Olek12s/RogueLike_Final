package ui;

import main.DrawPriorities;
import main.Drawable;
import org.w3c.dom.css.RGBColor;

import java.awt.*;

public class HUDRenderer implements Drawable
{
    HUD hud;
    int renderDebugInfoCounter;
    private String renderTime = "Render time: 0 ms";
    private String updateTime = "Update time: 0 ms";
    private String summaryTime = "Summary time: 0 ms";

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
        renderHealthBar(g2);
        if (hud.gc.isDebugMode()) renderDebugInfo(g2);
    }

    private void renderHealthBar(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));

        if (hud.gc.player.statistics.hitPoints < 0) hud.gc.player.statistics.hitPoints = 0;
        int healthPercent = (int) ((hud.gc.player.statistics.hitPoints / (double) hud.gc.player.getMaxHitPoints()) * 100);

        g2d.drawRect(45, 24, 300, 26);
        g2d.setColor(new Color(75, 61, 61, 210));
        g2d.fillRect(46, 25, 299, 25);
        g2d.setColor(Color.RED);
        g2d.fillRect(46, 25, (healthPercent*299)/100, 25);
        g2d.drawImage(hud.heart.image, 0, 0, hud.scale, hud.scale, null);
    }

    private void renderDebugInfo(Graphics g2)
    {
        renderDebugInfoCounter++;
        float renderTimeValue = hud.gc.getRenderTime() / 1_000_000.0f; // ns -> ms
        float updateTimeValue = hud.gc.getUpdateTime() / 1_000_000.0f; // ns -> ms
        float summaryTimeValue = renderTimeValue+updateTimeValue; // ms

        if (renderDebugInfoCounter == 60)   // updat evalues once per x seconds
        {
            renderTime = "Render time: " + renderTimeValue + "ms";
            updateTime = "Update time: " + updateTimeValue + "ms";
            summaryTime = "Summary time: " + summaryTimeValue + " ms";
            renderDebugInfoCounter = 0;
        }
        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.WHITE);
        int baseFontSize = 14;
        int scaledFontSize = (int)(baseFontSize * hud.scale/64);
        g2d.setFont(new Font("Arial", Font.BOLD, scaledFontSize));

        int baseX = 10;
        int baseY = 250;
        int scaledX = (int)(baseX * (hud.scale / 64.0));
        int scaledY = (int)(baseY * (hud.scale / 64.0));

        g2d.drawString(renderTime, scaledX, scaledY);
        g2d.drawString(updateTime, scaledX, scaledY + scaledFontSize + 5);
        g2d.drawString(summaryTime, scaledX, scaledY + 2 * (scaledFontSize + 5));
    }
}
