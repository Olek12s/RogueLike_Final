package main.item.armor.helemt;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class RubyHelmet extends Helmet
{
    public RubyHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public RubyHelmet(GameController gc)
    {
        super(gc, ItemID.RUBY_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(4);
    }
}
