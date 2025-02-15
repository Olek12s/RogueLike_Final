package main.item.mobweapon;

import main.controller.GameController;
import main.item.Item;
import main.item.ItemID;
import main.item.ItemStatistics;
import utilities.Position;

public class SpiderWeapon extends MobWeapon
{

    public SpiderWeapon(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.SPIDER_WEAPON, worldPosition);
        this.meleeAttackWidth = 22;
        this.meleeAttackHeight = 5;
        this.attackPreparationTime = 7;
        this.attackRestTime = 35;
        this.statistics.setPhysicalDamage(1);
    }
}
