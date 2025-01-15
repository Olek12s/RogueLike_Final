package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class MediumHealthPotion extends HealthPotion
{
    public MediumHealthPotion(GameController gc)
    {
        super(gc, ItemID.MEDIUM_HP_POTION, 60);
    }

    public MediumHealthPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.MEDIUM_HP_POTION, worldPosition, 60);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Medium health potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
