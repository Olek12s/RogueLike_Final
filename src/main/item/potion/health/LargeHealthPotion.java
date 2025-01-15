package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class LargeHealthPotion extends HealthPotion
{
    public LargeHealthPotion(GameController gc)
    {
        super(gc, ItemID.LARGE_HP_POTION, 100);
    }

    public LargeHealthPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LARGE_HP_POTION, 100);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        this.setHitbox();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large health potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
