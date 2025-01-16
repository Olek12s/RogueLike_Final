package main.item.tool;

import main.controller.GameController;
import main.item.Item;
import main.item.ItemID;
import main.item.ItemRenderer;
import utilities.Hitbox;
import utilities.Position;

public class WoodenPickaxe extends Item
{

    public WoodenPickaxe(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_PICKAXE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenPickaxe(GameController gc)
    {
        super(gc, ItemID.WOODEN_PICKAXE);
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*2, slotPixelSize*2);
    }

    @Override
    public void setRenderer()
    {
        renderer = new ItemRenderer(this);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden pickaxe");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 2;
    }
}