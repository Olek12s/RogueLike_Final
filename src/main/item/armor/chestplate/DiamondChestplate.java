package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class DiamondChestplate extends Chestplate
{
    public DiamondChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public DiamondChestplate(GameController gc)
    {
        super(gc, ItemID.DIAMOND_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(6);
    }
}
