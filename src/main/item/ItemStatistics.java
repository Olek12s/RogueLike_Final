package main.item;

import java.awt.image.BufferedImage;

public class ItemStatistics
{

    private String itemName = "";
    private boolean isStackable = false;
    private int stackSize = 1;
    private int physicalDamage = 0;
    private int magicalDamage = 0;
    private int inevitableDamage = 0;

    public ItemStatistics()
    {

    }

    public String getItemName() {return itemName;}
    public void setItemName(String itemName) {this.itemName = itemName;}
    public boolean isStackable() {return isStackable;}
    public void setStackable(boolean stackable) {isStackable = stackable;}
    public int getStackSize() {return stackSize;}
    public void setStackSize(int stackSize) {this.stackSize = stackSize;}
    public int getPhysicalDamage() {return physicalDamage;}
    public void setPhysicalDamage(int physicalDamage) {this.physicalDamage = physicalDamage;}
    public int getMagicalDamage() {return magicalDamage;}
    public void setMagicalDamage(int magicalDamage) {this.magicalDamage = magicalDamage;}
    public int getInevitableDamage() {return inevitableDamage;}
    public void setInevitableDamage(int inevitableDamage) {this.inevitableDamage = inevitableDamage;}
}
