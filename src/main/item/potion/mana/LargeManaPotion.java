package main.item.potion.mana;

import main.controller.GameController;
import main.item.ItemID;

public class LargeManaPotion extends ManaPotion
{
    public LargeManaPotion(GameController gc, ItemID itemID)
    {
        super(gc, itemID, 100);
    }

    @Override
    public void setStatistics()
    {
        itemStatistics.setItemName("Large mana potion");
        itemStatistics.setStackable(false);
        itemStatistics.setStackSize(1);
    }
}
