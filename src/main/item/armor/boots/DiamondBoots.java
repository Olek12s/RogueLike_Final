package main.item.armor.boots;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class DiamondBoots extends Boots
{
    public DiamondBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public DiamondBoots(GameController gc)
    {
        super(gc, ItemID.DIAMOND_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(3);
    }
}
