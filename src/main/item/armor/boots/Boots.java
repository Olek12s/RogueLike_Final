package main.item.armor.boots;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Boots extends Item
{
    public Boots(GameController gc, ItemID itemID, Position worldPosition)
    {
        super(gc, itemID, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public Boots(GameController gc, ItemID itemID)
    {
        super(gc, itemID);
    }

    @Override
    public void setHitbox() { hitbox = new Hitbox(worldPosition, slotPixelSize*2, slotPixelSize*1); }

    @Override
    public void setRenderer() {renderer = new ItemRenderer(this);}

    @Override
    public void setStatistics() {
        statistics.setItemName("Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth() {slotWidth = 2;}

    @Override
    public void setSlotHeight() {slotHeight = 1;}

    @Override
    public void setItemType() {itemType = ItemType.ARMOR;}

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.BOOTS;}
}
