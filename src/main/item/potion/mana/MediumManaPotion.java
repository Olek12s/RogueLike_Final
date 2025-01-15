package main.item.potion.mana;

import main.controller.GameController;
import main.item.ItemID;

public class MediumManaPotion extends ManaPotion
{
    public MediumManaPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 60);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Medium mana potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
