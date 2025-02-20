package main.item.armor.boots;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class RubyBoots extends Boots
{
    public RubyBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public RubyBoots(GameController gc)
    {
        super(gc, ItemID.RUBY_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(3);
    }
}
