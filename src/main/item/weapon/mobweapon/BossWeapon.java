package main.item.weapon.mobweapon;

import main.controller.GameController;
import main.item.ItemID;

public class BossWeapon extends MobWeapon
{
    public BossWeapon(GameController gc)
    {
        super(gc, ItemID.BOSS_WEAPON);
        this.meleeAttackWidth = 25;
        this.meleeAttackHeight = 70;
        this.attackPreparationTime = 10;
        this.attackRestTime = 30;
        this.statistics.setPhysicalDamage(5);
    }
}
