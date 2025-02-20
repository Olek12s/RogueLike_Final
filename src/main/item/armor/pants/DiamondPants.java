package main.item.armor.pants;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class DiamondPants extends Pants
{
    public DiamondPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public DiamondPants(GameController gc)
    {
        super(gc, ItemID.DIAMOND_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(5);
    }
}
