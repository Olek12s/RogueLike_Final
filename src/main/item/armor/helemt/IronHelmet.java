package main.item.armor.helemt;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class IronHelmet extends Helmet
{
    public IronHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public IronHelmet(GameController gc)
    {
        super(gc, ItemID.IRON_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(4);
    }
}
