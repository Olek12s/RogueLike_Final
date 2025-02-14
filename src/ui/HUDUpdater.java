package ui;

import main.controller.GameState;
import main.controller.Updatable;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.inventory.SlotType;
import main.item.Item;
import main.item.ItemSubType;
import main.item.ItemType;
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

        boolean droppedItemThisFrame = false;

        if (mh.leftButtonClicked)
        {
            droppedItemThisFrame = checkDropHeldItemOnClick();  // set boolean if dropped item
            if (!droppedItemThisFrame)
            {
                checkPickUpClickedItemFromSlot(); // if nothing was dropped in this frame, check picking
            }
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
        if (hud.gc.player.getInventory().getHeldItem() != null) return;
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
        System.out.println("picked up");
    }

    public boolean checkDropHeldItemOnClick()
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
                return true;
            }
            else    // if clicked on the slot
            {
                checkClickedOnSlotWhileHoldingItem(slot);
                return true;
            }
        }
        return false;
    }

    private void checkClickedOnSlotWhileHoldingItem(Slot clickedSlot)
    {
        Inventory playerInventory = hud.gc.player.getInventory();
        int slotIndexX = clickedSlot.getRowNum();
        int slotIndexY = clickedSlot.getColNum();

        Item heldItem = playerInventory.getHeldItem();
        Item mainInvItem = playerInventory.getItemAtFromMainInventory(slotIndexX, slotIndexY);

        if (clickedSlot.getSlotType() == SlotType.mainInvSlot)  // if clicked on main inv
        {
            if (mainInvItem == null)
            {
                System.out.println("0");
                if (playerInventory.addItem(heldItem, slotIndexX, slotIndexY))  // if can add item on the empty slot
                {
                    playerInventory.setHeldItem(null);
                }
            }
            else if (heldItem.getSlotWidth() == mainInvItem.getSlotWidth() && heldItem.getSlotHeight() == mainInvItem.getSlotHeight()) // if clicked on main inv and item (item sizes are the same)
            {
                playerInventory.removeItemFromMainInv(slotIndexX, slotIndexY);
                playerInventory.setHeldItem(mainInvItem);
                playerInventory.addItem(heldItem, slotIndexX, slotIndexY);
            }
        }
        else if (clickedSlot.getSlotType() == SlotType.beltSlot)    // if clicked on belt
        {
            Item beltItem = playerInventory.getItemAtFromBelt(slotIndexX);

            if (beltItem == null && heldItem != null)
            {
                playerInventory.getBeltSlots()[slotIndexX].setStoredItem(heldItem);
                playerInventory.setHeldItem(null);
            }
            else if (beltItem != null && heldItem != null)
            {
                Item slotItem = playerInventory.getItemAtFromBelt(slotIndexX);
                playerInventory.getBeltSlots()[slotIndexX].setStoredItem(heldItem);
                playerInventory.setHeldItem(slotItem);
            }
        }
        else     // if clicked on equipped
        {
            if (heldItem.getItemType() != ItemType.ARMOR) return;
            Item storedItem;
            switch(clickedSlot.getSlotType())
            {
                case helmetSlot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.HELMET) return;
                    storedItem = playerInventory.getHelmetSlot().getStoredItem();
                    playerInventory.getHelmetSlot().setStoredItem(heldItem);
                    break;
                }
                case chestplateSlot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.CHESTPLATE) return;
                    storedItem = playerInventory.getChestplateSlot().getStoredItem();
                    playerInventory.getChestplateSlot().setStoredItem(heldItem);
                    break;
                }
                case pantsSlot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.PANTS) return;
                    storedItem = playerInventory.getPantsSlot().getStoredItem();
                    playerInventory.getPantsSlot().setStoredItem(heldItem);
                    break;
                }
                case bootsSlot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.BOOTS) return;
                    storedItem = playerInventory.getBootsSlot().getStoredItem();
                    playerInventory.getBootsSlot().setStoredItem(heldItem);
                    break;
                }
                case shieldSlot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.SHIELD) return;
                    storedItem = playerInventory.getShieldSlot().getStoredItem();
                    playerInventory.getShieldSlot().setStoredItem(heldItem);
                    break;
                }
                case ring1Slot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.RING) return;
                    storedItem = playerInventory.getRing1Slot().getStoredItem();
                    playerInventory.getRing1Slot().setStoredItem(heldItem);
                    break;
                }
                case ring2Slot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.RING) return;
                    storedItem = playerInventory.getRing2Slot().getStoredItem();
                    playerInventory.getRing2Slot().setStoredItem(heldItem);
                    break;
                }
                case amuletSlot:
                {
                    if (heldItem.getItemSubType() != ItemSubType.AMULET) return;
                    storedItem = playerInventory.getAmuletSlot().getStoredItem();
                    playerInventory.getAmuletSlot().setStoredItem(heldItem);
                    break;
                }
                default: return;    // leave function
            }
            playerInventory.setHeldItem(storedItem);
        }
    }
}
