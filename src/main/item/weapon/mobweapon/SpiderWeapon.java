package main.item.weapon.mobweapon;

import main.controller.GameController;
import main.item.ItemID;

public class SpiderWeapon extends MobWeapon
{

    public SpiderWeapon(GameController gc)
    {
        super(gc, ItemID.SPIDER_WEAPON);
        this.meleeAttackWidth = 12;
        this.meleeAttackHeight = 30;
        this.attackPreparationTime = 0;
        this.attackRestTime = 15;
        this.statistics.setPhysicalDamage(1);
    }
}
