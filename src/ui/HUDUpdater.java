package ui;

import main.Updatable;

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
        hud.scaleX = hud.gc.getWidth() / 16;
        hud.scaleY = hud.gc.getHeight() / 9;
        hud.scale = Math.min(hud.scaleX, hud.scaleY);

        int currentHealth = hud.gc.player.hitPoints;
        int maxHealth = hud.gc.player.getMaxHitPoints();

        double healthRatio = (double) currentHealth / maxHealth;
        System.out.println(currentHealth);

        int tick = 0;
        if (healthRatio < 0.8) tick = 1;
        if (healthRatio < 0.6) tick = 2;
        if (healthRatio < 0.4) tick = 3;
        if (healthRatio < 0.2) tick = 4;

        hud.heart = hud.heartSpriteSheet.extractSprite(hud.heartSpriteSheet, tick, 0);
    }
}
