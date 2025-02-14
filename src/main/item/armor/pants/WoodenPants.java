package main.item.armor.pants;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class WoodenPants extends Pants
{
    public WoodenPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenPants(GameController gc)
    {
        super(gc, ItemID.WOODEN_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(5);
    }
}
