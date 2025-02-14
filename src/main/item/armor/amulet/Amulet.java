package main.item.armor.amulet;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Amulet extends Item
{

    protected Amulet(GameController gc, ItemID itemID, Position worldPosition)
    {
        super(gc, itemID, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    protected Amulet(GameController gc, ItemID itemID)
    {
        super(gc, itemID);
    }

    @Override
    public void setHitbox() { hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*1); }

    @Override
    public void setRenderer() {renderer = new ItemRenderer(this);}

    @Override
    public void setStatistics() {
        statistics.setItemName("Amulet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth() {slotWidth = 1;}

    @Override
    public void setSlotHeight() {slotHeight = 1;}

    @Override
    public void setItemType() {itemType = ItemType.ARMOR;}

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.AMULET;}
}

