package ui;

import main.DrawPriorities;
import main.Drawable;

import java.awt.*;

public class HUDRenderer implements Drawable
{
    HUD hud;
    int renderDebugInfoCounter;
    private String renderTime = "-";
    private String updateTime = "-";
    private String summaryTime = "-";
    private String drawCount = "-";

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
        if (hud.gc.isDebugMode()) renderDebugInfoLeft(g2);
        hud.gc.incrementRenderCount();
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
        g2d.fillRect(46, 25, (healthPercent * 299) / 100, 25);
        g2d.drawImage(hud.heart.image, 0, 0, hud.scale, hud.scale, null);
    }

    private void renderDebugInfoLeft(Graphics g2)
    {
        renderDebugInfoCounter++;
        if (renderDebugInfoCounter >= 60)
        {
            updateTimers();
            updateDrawCount();
            renderDebugInfoCounter = 0;
        }

        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.WHITE);
        int baseFontSize = 14;
        int scaledFontSize = (int) (baseFontSize * hud.scale / 64);
        g2d.setFont(new Font("Arial", Font.BOLD, scaledFontSize));

        int baseX = 10;
        int baseY = 250;
        int scaledX = (int) (baseX * (hud.scale / 64.0));
        int scaledY = (int) (baseY * (hud.scale / 64.0));

        String debugInfo = getTimers() + "\n" + drawCount;
        String[] debugLines = debugInfo.split("\\n");

        for (int i = 0; i < debugLines.length; i++) {
            g2d.drawString(debugLines[i], scaledX, scaledY + i * (scaledFontSize + 5));
        }
    }

    private void updateTimers()
    {
        renderTime = String.format("Render time: %.2f ms (%.2f%%)",
                hud.gc.getRenderTime() / 1_000_000.0f,
                (hud.gc.getRenderTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetDrawFrame()) * 100.0f);

        updateTime = String.format("Update time: %.2f ms (%.2f%%)",
                hud.gc.getUpdateTime() / 1_000_000.0f,
                (hud.gc.getUpdateTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetLogicFrame()) * 100.0f);

        summaryTime = String.format("Summary time: %.2f ms (%.2f%%)",
                (hud.gc.getRenderTime() + hud.gc.getUpdateTime()) / 1_000_000.0f,
                ((hud.gc.getRenderTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetDrawFrame()) * 100.0f) +
                        ((hud.gc.getUpdateTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetLogicFrame()) * 100.0f));
    }

    private String getTimers()
    {
        return renderTime + "\n" + updateTime + "\n" + summaryTime;
    }

    private void updateDrawCount()
    {
        long count = hud.gc.getRenderCount();
        drawCount = "Draw count: " + count;
    }
}