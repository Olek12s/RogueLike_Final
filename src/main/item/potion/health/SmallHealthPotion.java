package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class SmallHealthPotion extends HealthPotion
{
    public SmallHealthPotion(GameController gc)
    {
        super(gc, ItemID.SMALL_HP_POTION, 30);
    }

    public SmallHealthPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.SMALL_HP_POTION, 30);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        this.setHitbox();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Small health potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
