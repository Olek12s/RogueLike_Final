package main.item.armor.shield;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class RubyShield extends Shield
{
    public RubyShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public RubyShield(GameController gc)
    {
        super(gc, ItemID.RUBY_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(10);
        statistics.setMovementSpeedPenalty(1.3f);
    }
}
