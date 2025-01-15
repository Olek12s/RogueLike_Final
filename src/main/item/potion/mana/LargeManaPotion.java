package main.item.potion.mana;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class LargeManaPotion extends ManaPotion
{
    public LargeManaPotion(GameController gc)
    {
        super(gc, ItemID.LARGE_MANA_POTION, 100);
    }

    public LargeManaPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LARGE_MANA_POTION, 100);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        this.setHitbox();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large mana potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
