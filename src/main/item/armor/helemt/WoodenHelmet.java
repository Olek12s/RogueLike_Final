package main.item.armor.helemt;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class WoodenHelmet extends Helmet
{
    public WoodenHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenHelmet(GameController gc)
    {
        super(gc, ItemID.WOODEN_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(4);
    }
}
