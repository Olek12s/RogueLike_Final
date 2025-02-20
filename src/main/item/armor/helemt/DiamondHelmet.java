package main.item.armor.helemt;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class DiamondHelmet extends Helmet
{
    public DiamondHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public DiamondHelmet(GameController gc)
    {
        super(gc, ItemID.DIAMOND_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(4);
    }
}
