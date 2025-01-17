package ui;

import main.controller.Updatable;
import utilities.MouseHandler;

public class HUDUpdater implements Updatable
{
    HUD hud;

    public HUDUpdater(HUD hud)
    {
        this.hud = hud;
        hud.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        updateHealthBar();
        checkClick();
    }

    private void checkClick()
    {
        MouseHandler mh = hud.gc.mouseHandler;

        if (mh.leftButtonPressed && hud.hudRenderer.ring1Slot.isWithinSlot(mh.getClickPosition()))
        {
            System.out.println("in slot");
        }
    }

    private void updateHealthBar()
    {
        hud.scaleX = hud.gc.getWidth() / 16;
        hud.scaleY = hud.gc.getHeight() / 9;
        hud.scale = Math.min(hud.scaleX, hud.scaleY);

        int currentHealth = hud.gc.player.statistics.hitPoints;
        int maxHealth = hud.gc.player.getMaxHitPoints();

        double healthRatio = (double) currentHealth / maxHealth;

        int tick = 0;
        if (healthRatio < 0.8) tick = 1;
        if (healthRatio < 0.6) tick = 2;
        if (healthRatio < 0.4) tick = 3;
        if (healthRatio < 0.2) tick = 4;

        hud.heart = hud.heartSpriteSheet.extractSprite(hud.heartSpriteSheet, tick, 0);
    }
}
