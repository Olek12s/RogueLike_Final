package main.item.armor.pants;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class RubyPants extends Pants
{
    public RubyPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public RubyPants(GameController gc)
    {
        super(gc, ItemID.RUBY_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(5);
    }
}
