package main.item.ingredients;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Diamond extends Item
{
    public Diamond(GameController gc)
    {
        super(gc, ItemID.DIAMOND);
    }

    public Diamond(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND, worldPosition);
        this.setOnGround(true);
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize, slotPixelSize);
    }

    @Override
    public void setRenderer()
    {
        renderer = new ItemRenderer(this);
    }

    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 1;
    }

    @Override
    public void setItemType() { itemType = ItemType.INGREDIENT; }

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.INGREDIENT;}

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
