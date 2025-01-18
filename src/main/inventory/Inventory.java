package main.inventory;

import main.item.Item;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

public class Inventory
{
    public static final int INVENTORY_WIDTH_SLOTS = 9;
    public static final int INVENTORY_HEIGHT_SLOTS = 4;
    public static int beltWidthSlots = 6;

    private List<Item> itemList;
    private List<Item> beltItemList;
    private Item[][] slots;

    public List<Item> getItems() {return itemList;}
    public static int getBeltWidthSlots() {return beltWidthSlots;}
    public static void setBeltWidthSlots(int beltWidthSlots) {Inventory.beltWidthSlots = beltWidthSlots;}
    public List<Item> getItemList() {return itemList;}
    public void setItemList(List<Item> itemList) {this.itemList = itemList;}
    public List<Item> getBeltItemList() {return beltItemList;}
    public void setBeltItemList(List<Item> beltItemList) {this.beltItemList = beltItemList;}

    public Inventory()
    {
        slots = new Item[INVENTORY_WIDTH_SLOTS][INVENTORY_HEIGHT_SLOTS];
        itemList = new ArrayList<>();
        beltItemList = new ArrayList<>();
    }

    public boolean addItem(Item item, int slotX, int slotY)
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
                if (slots[i][j] != null)
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
               slots[i][j] = item;
            }
        }

        item.setInventoryPosition(new Position(slotX, slotY));
        itemList.add(item);
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

    public void removeItem(Item item)
    {
        Position itemPosition = item.getInventoryPosition();
        if (itemPosition == null) return;

        for (int i = itemPosition.x; i < itemPosition.x + item.getSlotWidth(); i++)
        {
            for (int j = itemPosition.y; j < itemPosition.y + item.getSlotHeight(); j++)
            {
                slots[i][j] = null;
            }
        }

        item.setInventoryPosition(null);
        itemList.remove(item);
    }

    public void removeItem(int x, int y)
    {
        Item item = getItemAt(x, y);
        if (item != null)
        {
            removeItem(item);
        }
    }

    public Item getItemAt(int x, int y)
    {
        if (x < 0 || x >= INVENTORY_WIDTH_SLOTS || y < 0 || y >= INVENTORY_HEIGHT_SLOTS) // boundaries
        {
            return null;
        }
        return slots[x][y];
    }

    public boolean moveItem(Item item, int newX, int newY)
    {
        Position oldPosition = item.getInventoryPosition();
        if (oldPosition == null) return false;

        // check if new position is valid
        if (newX + item.getSlotWidth() > INVENTORY_WIDTH_SLOTS || newY + item.getSlotHeight() > INVENTORY_HEIGHT_SLOTS)
        {
            return false;
        }

        // check if new position is not occupied by other items
        for (int i = newX; i < newX + item.getSlotWidth(); i++)
        {
            for (int j = newY; j < newY + item.getSlotHeight(); j++)
            {
                if (slots[i][j] != null && slots[i][j] != item)
                {
                    return false;
                }
            }
        }

        // dispode old slots
        for (int i = oldPosition.x; i < oldPosition.x + item.getSlotWidth(); i++)
        {
            for (int j = oldPosition.y; j < oldPosition.y + item.getSlotHeight(); j++)
            {
                slots[i][j] = null;
            }
        }

        // occupy new slots
        for (int i = newX; i < newX + item.getSlotWidth(); i++)
        {
            for (int j = newY; j < newY + item.getSlotHeight(); j++)
            {
                slots[i][j] = item;
            }
        }

        item.setInventoryPosition(new Position(newX, newY));
        return true;
    }
}
