package main.item.weapon.sword;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Sword extends Item
{
    public Sword(GameController gc, ItemID itemID, Position worldPosition)
    {
        super(gc, itemID, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public Sword(GameController gc, ItemID itemID)
    {
        super(gc, itemID);
    }


    @Override
    public void setRenderer()
    {
        renderer = new ItemRenderer(this);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Sword");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*2);
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
    public void setItemType() { itemType = ItemType.WEAPON;}

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.SWORD;}
}
