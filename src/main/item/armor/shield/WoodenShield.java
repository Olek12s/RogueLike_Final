package main.item.armor.shield;

import main.controller.GameController;
import main.item.ItemID;
import main.item.ItemRenderer;
import main.item.armor.shield.Shield;
import utilities.Hitbox;
import utilities.Position;

public class WoodenShield extends Shield
{

    public WoodenShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenShield(GameController gc)
    {
        super(gc, ItemID.WOODEN_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(10);
        statistics.setMovementSpeedPenalty(0.3f);
    }
}
