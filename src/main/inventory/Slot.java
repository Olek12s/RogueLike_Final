package main.inventory;

import main.item.Item;

public class Slot
{
    private Item storedItem;
    private SlotType slotType;
    private int width;
    private int height;
    private int rowNum;
    private int colNum;

    public Item getStoredItem() {return storedItem;}

    public SlotType getSlotType() {return slotType;}
    public void setStoredItem(Item storedItem) {this.storedItem = storedItem;}
    public void setSlotType(SlotType slotType) {this.slotType = slotType;}

    public int getRowNum() {return rowNum;}
    public int getColNum() {return colNum;}

    public int getWidth() {return width;}
    public int getHeight() {return height;}

    public Slot (SlotType slotType, int width, int height, int rowNum, int colNum)
    {
        this.storedItem = null;
        this.slotType = slotType;
        this.width = width;
        this.height = height;
        this.colNum = colNum;
        this.rowNum = rowNum;
    }
}
