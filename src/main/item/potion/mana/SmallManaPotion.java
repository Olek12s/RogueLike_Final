package main.item.potion.mana;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class SmallManaPotion extends ManaPotion
{
    public SmallManaPotion(GameController gc)
    {
        super(gc, ItemID.SMALL_MANA_POTION, 30);
    }

    public SmallManaPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.SMALL_MANA_POTION, worldPosition, 30);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Small mana potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
