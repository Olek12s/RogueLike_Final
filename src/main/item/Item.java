package main.item;

import main.controller.GameController;
import utilities.Hitbox;
import utilities.Position;
import utilities.sprite.Sprite;
import world.map.MapController;
import world.map.MapLevels;

public abstract class Item
{
    public static int slotPixelSize = 16;
    GameController gc;
    protected ItemRenderer renderer;
    //public Sprite sprite;
    protected int slotWidth;
    protected int slotHeight;
    protected Position worldPosition;
    private Position inventoryPosition;
    protected ItemStatistics statistics;
    protected ItemID itemID;
    private boolean isOnGround;
    protected Hitbox hitbox;

    private MapLevels level;

    public boolean isOnGround() {return isOnGround;}
    public void setOnGround(boolean onGround) {isOnGround = onGround;}

    public int getSlotWidth() {return slotWidth;}
    public int getSlotHeight() {return slotHeight;}
    public Position getInventoryPosition() { return inventoryPosition; }
    public void setInventoryPosition(Position inventoryPosition) { this.inventoryPosition = inventoryPosition; }
    public Position getWorldPosition() {return worldPosition;}
    public Hitbox getHitbox() {return hitbox;}
    public Sprite getSprite() {return ItemManager.getItemSprite(itemID);}

    public MapLevels getLevel() {return level;}
    public void setLevel(MapLevels level) {this.level = level;}

    // ABSTRACTS
    public abstract void setHitbox();
    public abstract void setRenderer();
    public abstract void setStatistics();
    public abstract void setSlotWidth();
    public abstract void setSlotHeight();
    // ABSTRACTS

    public Item(GameController gc, ItemID itemID, Position worldPosition)
    {
        this.gc = gc;
        this.itemID = itemID;
        this.statistics = new ItemStatistics();
        this.worldPosition = worldPosition;
        this.level = gc.mapController.getCurrentMap().getLevel();
        MapController.getCurrentMap().getChunk(worldPosition).addItem(this);
        setRenderer();
        setStatistics();
        setSlotWidth();
        setSlotHeight();
        setHitbox();
    }

    public Item(GameController gc, ItemID itemID)
    {
        this.gc = gc;
        this.itemID = itemID;
        this.statistics = new ItemStatistics();
        setRenderer();
        setStatistics();
        setSlotWidth();
        setSlotHeight();
    }

}
