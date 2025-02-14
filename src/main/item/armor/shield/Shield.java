package main.item.armor.shield;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class Shield extends Item
{
    public Shield(GameController gc, ItemID itemID, Position worldPosition)
    {
        super(gc, itemID, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    public Shield(GameController gc, ItemID itemID)
    {
        super(gc, itemID);
    }

    @Override
    public void setHitbox() { hitbox = new Hitbox(worldPosition, slotPixelSize*3, slotPixelSize*3); }

    @Override
    public void setRenderer() {renderer = new ItemRenderer(this);}

    @Override
    public void setStatistics() {
        statistics.setItemName("Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth() {slotWidth = 2;}

    @Override
    public void setSlotHeight() {slotHeight = 2;}

    @Override
    public void setItemType() {itemType = ItemType.ARMOR;}

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.SHIELD;}
}
