package main.item.armor;

import main.controller.GameController;
import main.item.Item;
import main.item.ItemID;
import main.item.ItemRenderer;
import utilities.Hitbox;
import utilities.Position;

public class WoodenShield extends Item
{

    public WoodenShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenShield(GameController gc)
    {
        super(gc, ItemID.WOODEN_SHIELD);
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
        statistics.setItemName("Wooden Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth()
    {
        slotWidth = 2;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 2;
    }
}
