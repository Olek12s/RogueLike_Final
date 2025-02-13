package ui;

import main.controller.GameState;
import main.controller.Updatable;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.inventory.SlotType;
import utilities.MouseHandler;
import utilities.Position;

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
        MouseHandler mh = hud.gc.mouseHandler;
        hud.hudRenderer.updateSizes();
        updateHealthBar();

        if (mh.leftButtonClicked)
        {
            checkPickUpClickedItemFromSlot();
            checkDropHeldItemOnClick();
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

    private Slot getClickedInventorySlot()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        if (mh.leftButtonClicked && hud.gc.gameStateController.getCurrentGameState() == GameState.INVENTORY)
        {
            Position clickPos = mh.getClickPosition();
            Slot slot =  hud.hudRenderer.getSlotAtPosition(clickPos);
            if (slot != null)
            {
                //System.out.println(slot.getRowNum() + " " + slot.getColNum() + " " + slot.getSlotType());
                //deleteItemFromSlotOnClick(slot);
            }
            else
            {
                //System.out.println("null");
            }
            return slot;
        }
        return null;
    }

    private void checkPickUpClickedItemFromSlot()
    {
        Slot slot = getClickedInventorySlot();
        if (slot == null || slot.getStoredItem() == null) return;

        hud.gc.player.getInventory().setHeldItem(slot.getStoredItem());
        deleteItemFromSlotOnClick(slot);
    }

    private void deleteItemFromSlotOnClick(Slot slot)
    {
        if (slot.getSlotType() == SlotType.mainInvSlot) hud.gc.player.getInventory().removeItemFromMainInv(slot.getRowNum(), slot.getColNum());
        else if (slot.getSlotType() == SlotType.beltSlot) hud.gc.player.getInventory().removeItemFromBelt(slot.getRowNum());
        else
        {
            SlotType slotType = slot.getSlotType();
            switch(slotType)
            {
                case beltSlot:
                case helmetSlot:
                case chestplateSlot:
                case pantsSlot:
                case bootsSlot:
                case shieldSlot:
                case ring1Slot:
                case ring2Slot:
                case amuletSlot:
                    hud.gc.player.getInventory().removeItemFromEquipped(slot); break;
                default: return;
            }
        }
    }

    public void checkDropHeldItemOnClick()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        Inventory playerInventory = hud.gc.player.getInventory();
        if (mh.leftButtonClicked && hud.gc.gameStateController.getCurrentGameState() == GameState.INVENTORY && playerInventory.getHeldItem() != null)
        {
            Position clickPos = mh.getClickPosition();
            Slot slot =  hud.gc.hud.getHudRenderer().getSlotAtPosition(clickPos);

            if (slot == null)   // if clicked outside of slot
            {
                playerInventory.dropItemOnGround(playerInventory.getHeldItem());
            }
        }
    }
}
