package main.item.mobweapon;

import main.controller.GameController;
import main.item.Item;
import main.item.ItemID;
import utilities.Position;

public class BareHands extends MobWeapon
{
    public BareHands(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.HANDS_WEAPON, worldPosition);
        this.meleeAttackWidth = 6;
        this.meleeAttackHeight = 30;
        this.attackPreparationTime = 2;
        this.attackRestTime = 25;
        this.statistics.setPhysicalDamage(1);
    }
}
