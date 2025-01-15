package main.item.potion.energy;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class LargeEnergyPotion extends EnergyPotion
{
    public LargeEnergyPotion(GameController gc)
    {
        super(gc, ItemID.LARGE_ENERGY_POTION, 100);
    }

    public LargeEnergyPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LARGE_ENERGY_POTION, worldPosition, 100);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large energy potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
