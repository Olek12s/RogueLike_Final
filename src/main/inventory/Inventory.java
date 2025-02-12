package main.inventory;

import main.controller.GameController;
import main.item.Item;
import main.item.armor.WoodenShield;
import main.item.potion.health.SmallHealthPotion;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

public class Inventory
{
    GameController gc;
    public static final int INVENTORY_WIDTH_SLOTS = 9;
    public static final int INVENTORY_HEIGHT_SLOTS = 4;
    public static final int INVENTORY_BELT_SLOTS = 6;

    private List<Item> inventoryItemList;
    private Slot[][] inventorySlots;
    private Slot[] beltSlots;
    private Slot helmetSlot;
    private Slot chestplateSlot;
    private Slot pantsSlot;
    private Slot bootsSlot;
    private Slot shieldSlot;
    private Slot ring1Slot;
    private Slot ring2Slot;
    private Slot amuletSlot;

    public List<Item> getInventoryItemList() {return inventoryItemList;}
    public List<Item> getBeltItemList()
    {
        List<Item> beltItems = new ArrayList<>();
        for (Slot slot : beltSlots) {
            if (slot.getStoredItem() != null)
            {
                beltItems.add(slot.getStoredItem());
            }
        }
        return beltItems;
    }

    public Slot[][] getInventorySlots() {return inventorySlots;}
    public Slot[] getBeltSlots() {return beltSlots;}
    public Slot getHelmetSlot() {return helmetSlot;}
    public Slot getChestplateSlot() {return chestplateSlot;}
    public Slot getPantsSlot() {return pantsSlot;}
    public Slot getBootsSlot() {return bootsSlot;}
    public Slot getShieldSlot() {return shieldSlot;}
    public Slot getRing1Slot() {return ring1Slot;}
    public Slot getRing2Slot() {return ring2Slot;}
    public Slot getAmuletSlot() {return amuletSlot;}

    public Inventory(GameController gc)
    {
        this.gc = gc;
        inventorySlots = new Slot[INVENTORY_WIDTH_SLOTS][INVENTORY_HEIGHT_SLOTS];
        for (int i = 0; i < INVENTORY_WIDTH_SLOTS; i++)
        {
            for (int j = 0; j < INVENTORY_HEIGHT_SLOTS; j++)
            {
                inventorySlots[i][j] = new Slot(SlotType.mainInvSlot, SlotType.getWidthMultipler(SlotType.mainInvSlot), SlotType.getHeightMultipler(SlotType.mainInvSlot), i, j);
            }
        }
        inventoryItemList = new ArrayList<>();
        beltSlots = new Slot[INVENTORY_BELT_SLOTS];
        for (int i = 0; i < INVENTORY_BELT_SLOTS; i++)
        {
            beltSlots[i] = new Slot(SlotType.beltSlot, SlotType.getWidthMultipler(SlotType.beltSlot), SlotType.getHeightMultipler(SlotType.beltSlot), i, 0);
        }

        helmetSlot = new Slot(SlotType.helmetSlot, SlotType.getWidthMultipler(SlotType.helmetSlot), SlotType.getHeightMultipler(SlotType.helmetSlot), 0, 0);
        chestplateSlot = new Slot(SlotType.chestplateSlot, SlotType.getWidthMultipler(SlotType.chestplateSlot), SlotType.getHeightMultipler(SlotType.chestplateSlot), 0, 0);
        pantsSlot = new Slot(SlotType.pantsSlot, SlotType.getWidthMultipler(SlotType.pantsSlot), SlotType.getHeightMultipler(SlotType.pantsSlot), 0, 0);
        bootsSlot = new Slot(SlotType.bootsSlot, SlotType.getWidthMultipler(SlotType.bootsSlot), SlotType.getHeightMultipler(SlotType.bootsSlot), 0, 0);
        shieldSlot = new Slot(SlotType.shieldSlot, SlotType.getWidthMultipler(SlotType.shieldSlot), SlotType.getHeightMultipler(SlotType.shieldSlot), 0, 0);
        ring1Slot = new Slot(SlotType.ring1Slot, SlotType.getWidthMultipler(SlotType.ring1Slot), SlotType.getHeightMultipler(SlotType.ring1Slot), 0, 0);
        ring2Slot = new Slot(SlotType.ring2Slot, SlotType.getWidthMultipler(SlotType.ring2Slot), SlotType.getHeightMultipler(SlotType.ring2Slot), 0, 0);
        amuletSlot = new Slot(SlotType.amuletSlot, SlotType.getWidthMultipler(SlotType.amuletSlot), SlotType.getHeightMultipler(SlotType.amuletSlot), 0, 0);
    }

    private boolean addItem(Item item, int slotX, int slotY)
    {

        if (slotX + item.getSlotWidth() > INVENTORY_WIDTH_SLOTS     // check if item's dimensions fit
                ||  slotY + item.getSlotHeight() > INVENTORY_HEIGHT_SLOTS)
        {
            return false;
        }

        // check if all required slots are not occupied by other items
        for (int i = slotX; i < slotX + item.getSlotWidth(); i++)
        {
            for (int j = slotY; j < slotY + item.getSlotHeight(); j++)
            {
                if (inventorySlots[i][j].getStoredItem() != null)
                {
                    return false;   // if slot is occupied - return null
                }
            }
        }

        // reserve required slots for item
        for (int i = slotX; i < slotX + item.getSlotWidth(); i++)
        {
            for (int j = slotY; j < slotY + item.getSlotHeight(); j++)
            {
                inventorySlots[i][j].setStoredItem(item);
            }
        }
        item.setInventoryPosition(new Position(slotX, slotY));
        inventoryItemList.add(item);
        return true;
    }

    public boolean addItem(Item item)
    {
        for (int x = 0; x < INVENTORY_WIDTH_SLOTS; x++)
        {
            for (int y = 0; y < INVENTORY_HEIGHT_SLOTS; y++)
            {
                if (addItem(item, x, y))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeItemFromMainInv(Item item)
    {
        Position itemPosition = item.getInventoryPosition();
        if (itemPosition == null) return;

        for (int i = itemPosition.x; i < itemPosition.x + item.getSlotWidth(); i++)
        {
            for (int j = itemPosition.y; j < itemPosition.y + item.getSlotHeight(); j++)
            {
                inventorySlots[i][j].setStoredItem(null);
            }
        }

        item.setInventoryPosition(null);
        inventoryItemList.remove(item);
    }

    /**
     *
     * @param x - x index of inventory
     * @param y - y index of inventory
     */
    public void removeItemFromMainInv(int x, int y)
    {
        Item item = getItemAt(x, y);
        if (item != null)
        {
            removeItemFromMainInv(item);
        }
    }

    /**
     *
     * @param x - x index of inventory
     * @param y - y index of inventory
     */
    public Item getItemAt(int x, int y)
    {
        if (x < 0 || x >= INVENTORY_WIDTH_SLOTS || y < 0 || y >= INVENTORY_HEIGHT_SLOTS) // boundaries
        {
            return null;
        }
        return inventorySlots[x][y].getStoredItem();
    }
}