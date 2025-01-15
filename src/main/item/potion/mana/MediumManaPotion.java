package main.item.potion.mana;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class MediumManaPotion extends ManaPotion
{
    public MediumManaPotion(GameController gc)
    {
        super(gc, ItemID.MEDIUM_MANA_POTION, 60);
    }

    public MediumManaPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.MEDIUM_MANA_POTION, 60);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        this.setHitbox();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Medium mana potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
