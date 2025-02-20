package main.item.ingredients;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class IronOre extends Item
{
    public IronOre(GameController gc)
    {
        super(gc, ItemID.IRON_ORE);
    }

    public IronOre(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_ORE, worldPosition);
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
        statistics.setItemName("Iron Ore");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
