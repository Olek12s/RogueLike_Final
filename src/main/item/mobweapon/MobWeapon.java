package main.item.mobweapon;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public class MobWeapon extends Item
{

    public MobWeapon(GameController gc, ItemID itemID, Position worldPosition) {
        super(gc, itemID, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(false);
    }

    public MobWeapon(GameController gc, ItemID itemID) {
        super(gc, itemID);
    }

    @Override
    public void setHitbox() { hitbox = new Hitbox(worldPosition, slotPixelSize*2, slotPixelSize*3); }

    @Override
    public void setRenderer() {renderer = new ItemRenderer(this);}

    @Override
    public void setStatistics() {
        statistics.setItemName("Mob Weapon");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    @Override
    public void setSlotWidth() {slotWidth = 1;}

    @Override
    public void setSlotHeight() {slotHeight = 1;}

    @Override
    public void setItemType() {itemType = ItemType.WEAPON;}

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.SWORD;}
}
