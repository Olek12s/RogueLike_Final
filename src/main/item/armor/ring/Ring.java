package main.item.armor.ring;

import main.controller.GameController;
import main.item.*;
import main.item.ingredients.Wood;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class Ring extends Item
{
    protected Ring(GameController gc, ItemID itemID, Position worldPosition)
    {
        super(gc, itemID, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    protected Ring(GameController gc, ItemID itemID)
    {
        super(gc, itemID);
    }

    @Override
    public void setHitbox() { hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*1); }

    @Override
    public void setRenderer() {renderer = new ItemRenderer(this);}

    @Override
    public void setStatistics() {
        statistics.setItemName("Ring");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth() {slotWidth = 1;}

    @Override
    public void setSlotHeight() {slotHeight = 1;}

    @Override
    public void setItemType() {itemType = ItemType.ARMOR;}

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.RING;}
}
