package main.item.tool.pickaxe;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Position;

public class WoodenPickaxe extends Pickaxe
{

    public WoodenPickaxe(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_PICKAXE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public WoodenPickaxe(GameController gc)
    {
        super(gc, ItemID.WOODEN_PICKAXE);
    }
}