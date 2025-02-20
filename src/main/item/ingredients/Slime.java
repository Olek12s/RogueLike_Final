package main.item.ingredients;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Slime extends Item
{
    public Slime(GameController gc)
    {
        super(gc, ItemID.SLIME);
    }

    public Slime(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.SLIME, worldPosition);
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
    public void setItemSubType() {itemSubType = ItemSubType.FLOWER;}

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
