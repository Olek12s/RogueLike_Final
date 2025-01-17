package ui;

import main.item.Item;
import utilities.Position;

public class ScreenSlot
{
    private Position screenPosition;
    private boolean isEmpty;
    private Item item;
    private int slotSize;

    public Position getScreenPosition() {return screenPosition;}
    public void setScreenPosition(Position screenPosition) {this.screenPosition = screenPosition;}
    public void setScreenPosition(int x, int y)
    {
        this.screenPosition.x = x;
        this.screenPosition.y = y;
    }
    public boolean isEmpty() {return isEmpty;}
    public void setEmpty(boolean empty) {isEmpty = empty;}
    public Item getItem() {return item;}
    public void setItem(Item item) {this.item = item;}
    public int getSlotSize() {return slotSize;}
    public void setSlotSize(int slotSize) {this.slotSize = slotSize;}

    public ScreenSlot(int slotSize, Position screenPosition)
    {
        this.slotSize = slotSize;
        this.screenPosition = screenPosition;
        this.isEmpty = false;
        this.item = null;
    }

    public boolean isWithinSlot(Position position)
    {
        return (position.x >= screenPosition.x) && (position.x <= screenPosition.x + slotSize )
                && (position.y >= screenPosition.y) && (position.y <= screenPosition.y + slotSize);
    }
}
