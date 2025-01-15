package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;

public class LargeHealthPotion extends HealthPotion
{
    public LargeHealthPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 100);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Large health potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
