package main.item.potion.energy;

import main.controller.GameController;
import main.item.ItemID;

public class SmallEnergyPotion extends EnergyPotion
{
    public SmallEnergyPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 30);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Small energy potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
