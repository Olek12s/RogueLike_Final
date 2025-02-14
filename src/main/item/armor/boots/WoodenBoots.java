package main.item.armor.boots;

import main.controller.GameController;
import main.item.Item;
import main.item.ItemID;
import main.item.ItemRenderer;
import utilities.Hitbox;
import utilities.Position;

public class WoodenBoots extends Boots
{
    public WoodenBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenBoots(GameController gc)
    {
        super(gc, ItemID.WOODEN_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}