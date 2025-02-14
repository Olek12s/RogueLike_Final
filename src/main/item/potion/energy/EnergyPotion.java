package main.item.potion.energy;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public abstract class EnergyPotion extends Item implements Consumable
{
    protected int regenerationPower;

    public EnergyPotion(GameController gc, ItemID itemID, int regenerationPower)
    {
        super(gc, itemID);
        this.regenerationPower = regenerationPower;
    }

    public EnergyPotion(GameController gc, ItemID itemID, Position worldPosition, int regenerationPower)
    {
        super(gc, itemID, worldPosition);
        this.regenerationPower = regenerationPower;
    }

    public void applyRegenerationEffect(int regenerationPower)
    {
        System.out.println("Energy regen " + regenerationPower);
    }

    @Override
    public void consume()
    {
        System.out.println("consumed");
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize, slotPixelSize);
    }

    @Override
    public void setRenderer()
    {
        renderer = new ItemRenderer(this);
    }

    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 1;
    }

    @Override
    public void setItemType() { itemType = ItemType.POTION; }

    @Override
    public void setItemSubType() {itemSubType = ItemSubType.ENERGY_POTION;}
}
