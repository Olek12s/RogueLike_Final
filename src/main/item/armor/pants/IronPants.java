package main.item.armor.pants;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class IronPants extends Pants
{
    public IronPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public IronPants(GameController gc)
    {
        super(gc, ItemID.IRON_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(5);
    }
}
