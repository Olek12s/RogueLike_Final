package main.item.potion.mana;

import main.controller.GameController;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

import javax.swing.*;

public abstract class ManaPotion extends Item implements Consumable
{
    protected int regenerationPower;

    public ManaPotion(GameController gc, ItemID itemID, int regenerationPower)
    {
        super(gc, itemID);
        this.regenerationPower = regenerationPower;
    }

    public ManaPotion(GameController gc, ItemID itemID, Position worldPosition, int regenerationPower)
    {
        super(gc, itemID, worldPosition);
        this.regenerationPower = regenerationPower;
    }

    public void applyRegenerationEffect(int regenerationPower)
    {
        System.out.println("Mana regen " + regenerationPower);
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
}
