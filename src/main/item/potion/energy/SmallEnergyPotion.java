package main.item.potion.energy;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class SmallEnergyPotion extends EnergyPotion
{
    public SmallEnergyPotion(GameController gc)
    {
        super(gc, ItemID.SMALL_ENERGY_POTION, 30);
    }

    public SmallEnergyPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.SMALL_ENERGY_POTION, worldPosition, 30);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Small energy potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
