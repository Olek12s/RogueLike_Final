package main.item.potion.mana;

import main.controller.GameController;
import main.item.ItemID;

public class SmallManaPotion extends ManaPotion
{
    public SmallManaPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 30);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Small mana potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
