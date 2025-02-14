package main.item.armor.ring;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class GelRing extends Ring
{
    public GelRing(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.GEL_RING, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public GelRing(GameController gc)
    {
        super(gc, ItemID.GEL_RING);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Gel Ring");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setMagicalArmor(4);
        statistics.setArmor(2);
    }
}
