package main.item.weapon.sword;

import main.controller.GameController;
import main.item.ItemID;
import utilities.Hitbox;
import utilities.Position;

public class LongIronSword extends Sword
{
    public LongIronSword(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LONG_IRON_SWORD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large Iron Sword");
        statistics.setStackable(false);
        meleeAttackHeight = 45;
        statistics.setPhysicalDamage(5);
        statistics.setMovementSpeedPenalty(1.5f);
        attackRestTime = 10;
    }


    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 3;
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*3);
    }
}
