package main.item.armor.boots;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class IronBoots extends Boots
{
    public IronBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public IronBoots(GameController gc)
    {
        super(gc, ItemID.IRON_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(3);
    }
}
