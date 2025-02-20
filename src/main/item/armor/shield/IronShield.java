package main.item.armor.shield;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class IronShield extends Shield
{
    public IronShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public IronShield(GameController gc)
    {
        super(gc, ItemID.IRON_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(10);
        statistics.setMovementSpeedPenalty(1.3f);
    }
}
