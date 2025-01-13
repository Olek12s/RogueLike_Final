package main.item;

import main.GameController;
import utilities.Position;
import utilities.sprite.Sprite;

public abstract class Item
{
    public static int slotPixelSize = 8;
    GameController gc;
    private ItemRenderer renderer;
    private Sprite sprite;
    private int slotWidth;
    private int slotHeight;
    private Position worldPosition;
    private Position inventoryPosition;
    private ItemStatistics statistics;
    private ItemID itemID;
    private boolean isOnGround;

    public boolean isOnGround() {return isOnGround;}
    public void setOnGround(boolean onGround) {isOnGround = onGround;}

    public int getSlotWidth() {return slotWidth;}
    public int getSlotHeight() {return slotHeight;}
    public Position getInventoryPosition() { return inventoryPosition; }
    public void setInventoryPosition(Position inventoryPosition) { this.inventoryPosition = inventoryPosition; }

    // ABSTRACTS
    public abstract void setHitbox();
    public abstract void setRenderer();
    public abstract void setStatistics();
    public abstract void setSlotWidth();
    public abstract void setSlotHeight();
    // ABSTRACTS

    public Item(GameController gc, ItemID itemID)
    {
        this.gc = gc;
        this.itemID = itemID;
    }

}
