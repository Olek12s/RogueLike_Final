package ui;

import main.controller.GameState;
import main.controller.Updatable;
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
    private boolean draggingItem;
    private Item draggedItem;

    public boolean isDraggingItem() {return draggingItem;}
    public Item getDraggedItem() {return draggedItem;}

    public HUDUpdater(HUD hud)
    {
        this.hud = hud;
        hud.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        updateHealthBar();
        checkStartDraggingFromSlot();
        checkEndDraggingFromSlot();
        //checkEndClick();
    }

    private boolean clickedOnSlot()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        if (mh.leftButtonClicked)
        {
            ScreenSlot slot = hud.getScreenSlotAt(mh.getClickPosition());
            if (slot == null) return false;
            else return slot.isWithinSlot(mh.getClickPosition());
        }
        return false;
    }

    private void checkStartDraggingFromSlot()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        if (clickedOnSlot())
        {
            ScreenSlot slot = hud.getScreenSlotAt(mh.getClickPosition());
            if (slot.getItem() != null)
            {
                if (slot.getSlotType() == SlotType.mainInvSlot)   // main inventory
                {
                    draggedItem = hud.gc.player.getInventory().getItemAt(slot.getSlotNumberX(), slot.getSlotNumberY());
                    draggingItem = true;
                    hud.gc.player.getInventory().removeItem(draggedItem);

                }
                else if (slot.getSlotType() == SlotType.beltSlot) // belt inventory
                {
                    draggedItem = hud.gc.player.getInventory().getItemAt(slot.getSlotNumberX(), 0);
                    draggingItem = true;
                    hud.gc.player.getInventory().removeItem(draggedItem);
                }
                else    // equipped inventory
                {

                }
            }
        }
    }

    private void checkEndDraggingFromSlot()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        if (!clickedOnSlot() && draggingItem)
        {
            Position worldPosition = Position.screenToWorldPosition(mh.getClickPosition().x, mh.getClickPosition().y);
            dropDraggedItemOnGround(worldPosition);
        }
    }

    private void dropDraggedItemOnGround(Position worldPosition)
    {
        draggingItem = false;
        draggedItem.setOnGround(true);
        draggedItem.setWorldPosition(worldPosition);

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
