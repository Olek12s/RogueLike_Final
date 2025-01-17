package ui;

import main.item.Item;
import utilities.Position;

public class ScreenSlot
{
    private Position screenPosition;
    private boolean isEmpty;
    private Item item;
    private int slotWidth;
    private int slotHeight;
    private SlotType slotType;

    public int getSlotWidth() {return slotWidth;}
    public int getSlotHeight() {return slotHeight;}
    public SlotType getSlotType() {return slotType;}

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

    public ScreenSlot(int slotSize, SlotType slotType)
    {
        this.slotType = slotType;
        this.slotWidth = SlotType.getSlotWidthMultipler(slotType) * slotSize;
        this.slotHeight = SlotType.getSlotHeightMultipler(slotType) * slotSize;
        this.isEmpty = false;
        this.item = null;
    }

    public void updateSlot(int slotSize, int screenX, int screenY)
    {
        if (screenPosition == null) screenPosition = new Position(screenX, screenY);
        this.slotWidth = SlotType.getSlotWidthMultipler(slotType) * slotSize;
        this.slotHeight = SlotType.getSlotHeightMultipler(slotType) * slotSize;
        this.screenPosition.x = screenX;
        this.screenPosition.y = screenY;
    }

    public boolean isWithinSlot(Position position)
    {
        return (position.x >= screenPosition.x) && (position.x <= screenPosition.x + slotWidth)
                && (position.y >= screenPosition.y) && (position.y <= screenPosition.y + slotHeight);
    }
}
