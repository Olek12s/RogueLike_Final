package main.item.ingredients;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Coal extends Item
{
    public Coal(GameController gc)
    {
        super(gc, ItemID.COAL);
    }

    public Coal(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.COAL, worldPosition);
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
        statistics.setItemName("Coal");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
