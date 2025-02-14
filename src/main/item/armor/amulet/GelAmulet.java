package main.item.armor.amulet;

import main.controller.GameController;
import main.item.ItemID;
import main.item.ItemRenderer;
import utilities.Hitbox;
import utilities.Position;

public class GelAmulet extends Amulet
{
    public GelAmulet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.GEL_AMULET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public GelAmulet(GameController gc)
    {
        super(gc, ItemID.GEL_AMULET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Gel Amulet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }
}
