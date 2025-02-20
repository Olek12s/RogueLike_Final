package main.item.armor.shield;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class DiamondShield extends Shield
{
    public DiamondShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public DiamondShield(GameController gc)
    {
        super(gc, ItemID.DIAMOND_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(10);
        statistics.setMovementSpeedPenalty(1.3f);
    }
}
