package main.item;

public class ZombieWeapon extends Weapon
{

    public ZombieWeapon()
    {
        this.itemStatistics = new ItemStatistics();
        this.meleeAttackWidth = 22;
        this.meleeAttackHeight = 6;
        this.attackPreparationTime = 15;
        this.attackRestTime = 60;
        this.itemStatistics.setPhysicalDamage(5);
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

    @Override
    public int getDamageOutput()
    {
        return itemStatistics.getPhysicalDamage();
    }
}

