package main.item.tool.axe;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Axe extends Item
{
    public Axe(GameController gc, ItemID itemID, Position worldPosition)
    {
        super(gc, itemID, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public Axe(GameController gc, ItemID itemID)
    {
        super(gc, itemID);
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*2);
    }

    @Override
    public void setRenderer()
    {
        renderer = new ItemRenderer(this);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Axe");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 2;
    }

    @Override
    public void setItemType() { itemType = ItemType.TOOL;}

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.AXE;}
}
