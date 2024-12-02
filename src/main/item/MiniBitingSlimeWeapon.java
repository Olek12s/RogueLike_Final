package main.item;

import utilities.Hitbox;

public class MiniBitingSlimeWeapon
{
    private ItemStatistics itemStatistics;
    private int meleeAttackWidth;
    private int meleeAttackHeight;
    private int attackPreparationTime;     // time in x/60 seconds of how long entity prepares an attack
    private int attackRestTime;           // time in x/60 seconds until next attack

    public MiniBitingSlimeWeapon()
    {
        this.itemStatistics = new ItemStatistics();
        this.meleeAttackWidth = 22;
        this.meleeAttackHeight = 3;
        this.attackPreparationTime = 10;
        this.attackRestTime = 30;
        this.itemStatistics.setPhysicalDamage(8);
    }

    public ItemStatistics getItemStatistics() {return itemStatistics;}
    public void setItemStatistics(ItemStatistics itemStatistics) {this.itemStatistics = itemStatistics;}
    public int getMeleeAttackWidth() {return meleeAttackWidth;}
    public void setMeleeAttackWidth(int meleeAttackWidth) {this.meleeAttackWidth = meleeAttackWidth;}
    public int getMeleeAttackHeight() {return meleeAttackHeight;}
    public void setMeleeAttackHeight(int meleeAttackHeight) {this.meleeAttackHeight = meleeAttackHeight;}
    public int getAttackPreparationTime() {return attackPreparationTime;}
    public void setAttackPreparationTime(int attackPreparationTime) {this.attackPreparationTime = attackPreparationTime;}
    public int getAttackRestTime() {return attackRestTime;}
    public void setAttackRestTime(int attackRestTime) {this.attackRestTime = attackRestTime;}
}
