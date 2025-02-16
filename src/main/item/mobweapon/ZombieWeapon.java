package main.item.mobweapon;

import main.controller.GameController;
import main.item.Item;
import main.item.ItemID;
import main.item.ItemStatistics;
import utilities.Position;

public class ZombieWeapon extends MobWeapon
{

    public ZombieWeapon(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.ZOMBIE_WEAPON, worldPosition);
        this.meleeAttackWidth = 8;
        this.meleeAttackHeight = 35;
        this.attackPreparationTime = 8;
        this.attackRestTime = 100;
        this.statistics.setPhysicalDamage(5);
    }
}

