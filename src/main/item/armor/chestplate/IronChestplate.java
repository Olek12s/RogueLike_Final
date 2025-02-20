package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class IronChestplate extends Chestplate
{
    public IronChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public IronChestplate(GameController gc)
    {
        super(gc, ItemID.IRON_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(6);
    }
}
