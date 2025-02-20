package main.item.weapon.sword;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Hitbox;
import utilities.Position;

public class IronSword extends Sword
{
    public IronSword(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_SWORD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);

    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Sword");
        statistics.setStackable(false);
        meleeAttackHeight = 30;
        statistics.setPhysicalDamage(3);
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
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*2);
    }
}
