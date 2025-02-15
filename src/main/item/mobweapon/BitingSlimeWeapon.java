package main.item.mobweapon;

import main.controller.GameController;
import main.item.Item;
import main.item.ItemID;
import main.item.ItemStatistics;
import utilities.Position;

public class BitingSlimeWeapon extends MobWeapon
{

    public BitingSlimeWeapon(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.BITING_SLIME_WEAPON, worldPosition);
        this.meleeAttackWidth = 84;
        this.meleeAttackHeight = 55;
        this.attackPreparationTime = 3;
        this.attackRestTime = 15;
        this.statistics.setPhysicalDamage(1);
    }
}
