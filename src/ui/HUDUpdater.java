package ui;

import main.controller.GameState;
import main.controller.Updatable;
import main.entity.EntityStatistics;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.inventory.SlotType;
import main.item.Crafting;
import main.item.Item;
import main.item.ItemSubType;
import main.item.ItemType;
import utilities.MouseHandler;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        checkLevelUpOnIconClick();
        checkCraftingArrowClick();
        checkCraftingRecipeClick();
    }

    private void updateHealthBar()
    {
        hud.scaleX = hud.gc.getWidth() / 16;
        hud.scaleY = hud.gc.getHeight() / 9;
        hud.scale = Math.min(hud.scaleX, hud.scaleY);

        int currentHealth = hud.gc.player.statistics.getHitPoints();
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

    /**
     * Performs an upgrade of the player's desired statistic on click. Resets EXP after an upgrade.
     */
    public void checkLevelUpOnIconClick()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        if (!mh.leftButtonClicked) return;
        if (hud.hudRenderer.getLevelUpIconPositions() == null) return;
        int iconSize = hud.hudRenderer.getScaledFontSize();
        int mouseX = mh.getMouseX();
        int mouseY = mh.getMouseY();

        for (int i = 0; i < hud.hudRenderer.getLevelUpIconPositions().length; i++)
        {
            Position buttonPosition = hud.hudRenderer.getLevelUpIconPositions()[i];
            if (buttonPosition != null)
            {
                if (mouseX >= buttonPosition.x && mouseX <= buttonPosition.x + iconSize && mouseY >= buttonPosition.y && mouseY <= buttonPosition.y + iconSize)
                {
                    String clickedText = "";
                    EntityStatistics playerStats = hud.gc.player.statistics;
                    switch(i)
                    {
                        case 0:
                        {
                            clickedText = "HP: " + hud.gc.player.statistics.getHitPoints() + " / " + hud.gc.player.statistics.getMaxHitPoints();
                            playerStats.setMaxHitPoints(EntityStatistics.HP_UPDATE_BONUS + playerStats.getMaxHitPoints());
                            break;
                        }
                        case 1:
                        {
                            clickedText = "Mana: " + hud.gc.player.statistics.getMana() + " / " + hud.gc.player.statistics.getMaxMana();
                            playerStats.setMaxMana(EntityStatistics.MANA_UPDATE_BONUS + playerStats.getMaxMana());
                            break;
                        }
                        case 2:
                        {
                            clickedText = "Armour: " + hud.gc.player.statistics.getArmour();
                            playerStats.setArmour(EntityStatistics.ARMOUR_UPDATE_BONUS + playerStats.getBaseArmour());
                            break;
                        }
                        case 3:
                        {
                            clickedText = "Magic Armour: " + hud.gc.player.statistics.getMagicArmour();
                            playerStats.setMagicArmour(EntityStatistics.MAGIC_ARMOUR_UPDATE_BONUS + playerStats.getBaseMagicArmour());
                            break;
                        }
                        case 4:
                        {
                            clickedText = "Strength: " + hud.gc.player.statistics.getStrength();
                            playerStats.setStrength(EntityStatistics.STRENGTH_UPDATE_BONUS + playerStats.getStrength());
                            break;
                        }
                        case 5:
                        {
                            clickedText = "Stamina: " + hud.gc.player.statistics.getStamina();
                            playerStats.setStamina(EntityStatistics.STAMINA_UPDATE_BONUS + playerStats.getStamina());
                            break;
                        }
                        case 6:
                        {
                            clickedText = "Regeneration: " + hud.gc.player.statistics.getRegeneration();
                            playerStats.setRegeneration(EntityStatistics.REGENERATION_UPDATE_BONUS + playerStats.getRegeneration());
                            break;
                        }
                        case 7:
                        {
                            clickedText = "Dexterity: " + hud.gc.player.statistics.getDexterity();
                            playerStats.setDexterity(EntityStatistics.DEXTERITY_UPDATE_BONUS + playerStats.getDexterity());
                            break;
                        }
                        case 8:
                        {
                            clickedText = "Intellect: " + hud.gc.player.statistics.getIntellect();
                            playerStats.setIntellect(EntityStatistics.INTELLECT_UPDATE_BONUS + playerStats.getIntellect());
                            break;
                        }
                        case 9:
                        {
                            //break;
                        }
                        case 10:
                        {
                            break;
                        }
                        default: break;
                    }
                    playerStats.setExp(playerStats.getExp() - playerStats.getNextLevelExp());   // reset exp
                    playerStats.setNextLevelExp((int)(playerStats.getNextLevelExp() * EntityStatistics.NEXT_LEVEL_EXP_MULTIPLER));
                    System.out.println(clickedText);
                    break;
                }
            }
        }
    }

    private void checkCraftingArrowClick()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        if (!mh.leftButtonClicked) return;
        if (hud.hudRenderer.getCraftingLeftArrowPosition() == null || hud.hudRenderer.getCraftingRightArrowPosition() == null) return;
        int mouseX = mh.getMouseX();
        int mouseY = mh.getMouseY();

        Position leftArrowPosition = hud.hudRenderer.getCraftingLeftArrowPosition();
        Position rightArrowPosition = hud.hudRenderer.getCraftingRightArrowPosition();
        int arrowWidth = hud.hudRenderer.getCraftingArrowWidth();
        int arrowHeight = hud.hudRenderer.getCraftingArrowHeight();
        int currentPage = hud.hudRenderer.getCurrentCraftingPage();
        // left arrow
        if (mouseX >= leftArrowPosition.x && mouseX <= leftArrowPosition.x + arrowWidth && mouseY >= leftArrowPosition.y && mouseY <= leftArrowPosition.y + arrowHeight)
        {
            if (currentPage != 0) hud.hudRenderer.setCurrentCraftingPage(currentPage - 1);
        }
        // right arrow
        else if (mouseX >= rightArrowPosition.x && mouseX <= rightArrowPosition.x + arrowWidth && mouseY >= rightArrowPosition.y && mouseY <= rightArrowPosition.y + arrowWidth)
        {
            if (currentPage < hud.hudRenderer.getRecipePagesCount()) hud.hudRenderer.setCurrentCraftingPage(currentPage + 1);
        }
    }

    private void checkCraftingRecipeClick()
    {
        MouseHandler mh = hud.gc.mouseHandler;
        if (!mh.leftButtonClicked) return;
        int mouseX = mh.getMouseX();
        int mouseY = mh.getMouseY();

        getClickedCrafting(mouseX, mouseY);
    }

    private Crafting getClickedCrafting(int mouseX, int mouseY)
    {
        Position[] subFramePositions = hud.hudRenderer.getCraftingSubFramePositions();
        int subFrameWidth = hud.hudRenderer.getCraftingSubFrameWidth();
        int subFrameHeight = hud.hudRenderer.getCraftingSubFrameHeight();
        List<Map.Entry<Item, Crafting>> recipeList = new ArrayList<>(Crafting.craftings.entrySet());
        int startIndex = hud.hudRenderer.getCurrentCraftingPage() * hud.hudRenderer.getRecipesPerPage();

        for (int i = 0; i < subFramePositions.length; i++)
        {
            Position subFramePosition = subFramePositions[i];

            if (mouseX >= subFramePosition.x && mouseX <= subFramePosition.x + subFrameWidth && mouseY >= subFramePosition.y && mouseY <= subFramePosition.y + subFrameHeight)
            {
                int recipeIndex = startIndex + i;
                if (recipeIndex < recipeList.size())
                {
                    String itemName = recipeList.get(recipeIndex).getKey().getStatistics().getItemName();
                    return recipeList.get(recipeIndex).getValue();
                }
            }
        }
        return null;    // returns null if no crafting was clicked
    }
}
