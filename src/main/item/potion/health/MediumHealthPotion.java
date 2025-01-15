package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;

public class MediumHealthPotion extends HealthPotion
{
    public MediumHealthPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 60);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Medium health potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
