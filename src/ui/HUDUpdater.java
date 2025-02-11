package ui;

import main.controller.GameController;
import main.controller.GameState;
import main.controller.Updatable;
import main.inventory.Slot;
import main.item.Item;
import main.item.ItemID;
import main.item.ItemManager;
import utilities.MouseHandler;
import utilities.Position;
import world.map.MapController;

import java.util.ArrayList;

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
        hud.hudRenderer.updateSizes();
        updateHealthBar();
        clickedOnSlot();
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

    private Slot clickedOnSlot()
    {
        return null;
    }
}
