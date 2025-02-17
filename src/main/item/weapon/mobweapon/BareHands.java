package main.item.weapon.mobweapon;

import main.controller.GameController;
import main.item.ItemID;
import main.item.weapon.mobweapon.MobWeapon;
import utilities.Position;

public class BareHands extends MobWeapon
{
    public BareHands(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.HANDS_WEAPON, worldPosition);
        this.meleeAttackWidth = 12;
        this.meleeAttackHeight = 30;
        this.attackPreparationTime = 0;
        this.attackRestTime = 5;
        this.statistics.setPhysicalDamage(1);
    }
}
