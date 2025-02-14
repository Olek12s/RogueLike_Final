package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class WoodenChestplate extends Chestplate
{
    public WoodenChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenChestplate(GameController gc)
    {
        super(gc, ItemID.WOODEN_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(6);
    }
}
