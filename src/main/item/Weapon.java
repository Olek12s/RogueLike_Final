package main.item;

public abstract class Weapon
{
    protected ItemStatistics itemStatistics;
    protected int meleeAttackWidth;
    protected int meleeAttackHeight;
    protected int attackPreparationTime;     // time in x/60 seconds of how long entity prepares an attack
    protected int attackRestTime;           // time in x/60 seconds until next attack

    public abstract int getDamageOutput();
}
