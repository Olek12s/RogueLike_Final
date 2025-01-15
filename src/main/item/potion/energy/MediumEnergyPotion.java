package main.item.potion.energy;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class MediumEnergyPotion extends EnergyPotion
{
    public MediumEnergyPotion(GameController gc)
    {
        super(gc, ItemID.MEDIUM_ENERGY_POTION, 60);
    }

    public MediumEnergyPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.MEDIUM_ENERGY_POTION, 60);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        this.setHitbox();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Medium energy potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
