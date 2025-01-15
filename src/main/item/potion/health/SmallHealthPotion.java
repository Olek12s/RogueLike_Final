package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;

public class SmallHealthPotion extends HealthPotion
{
    public SmallHealthPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 30);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Small health potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
