package main.item.potion.energy;

import main.controller.GameController;
import main.item.ItemID;

public class MediumEnergyPotion extends EnergyPotion
{
    public MediumEnergyPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 60);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Medium energy potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
