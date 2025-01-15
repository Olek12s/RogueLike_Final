package main.item.potion.energy;

import main.controller.GameController;
import main.item.ItemID;

public class LargeEnergyPotion extends EnergyPotion
{
    public LargeEnergyPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 100);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Large energy potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
