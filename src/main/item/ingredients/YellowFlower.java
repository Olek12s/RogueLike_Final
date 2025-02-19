package main.item.ingredients;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class YellowFlower extends Item
{
    public YellowFlower(GameController gc)
    {
        super(gc, ItemID.YELLOW_FLOWER);
    }

    public YellowFlower(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.YELLOW_FLOWER, worldPosition);
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
    public void setItemSubType() {itemSubType = ItemSubType.FLOWER;}

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Yellow Flower");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
