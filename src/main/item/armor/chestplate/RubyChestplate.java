package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class RubyChestplate extends Chestplate
{
    public RubyChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public RubyChestplate(GameController gc)
    {
        super(gc, ItemID.RUBY_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(6);
    }
}
