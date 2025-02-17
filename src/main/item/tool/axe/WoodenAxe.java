package main.item.tool.axe;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class WoodenAxe extends Axe
{

    public WoodenAxe(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_AXE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenAxe(GameController gc)
    {
        super(gc, ItemID.WOODEN_AXE);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Axe");
        statistics.setStackable(false);
        meleeAttackHeight = 30;
        attackRestTime = 20;
        statistics.setPhysicalDamage(2);
    }
}
