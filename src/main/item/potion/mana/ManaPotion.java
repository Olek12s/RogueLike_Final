package main.item.potion.mana;

import main.controller.GameController;
import main.entity.Entity;
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
        this.gc = gc;
        this.regenerationPower = regenerationPower;
    }

    public ManaPotion(GameController gc, ItemID itemID, Position worldPosition, int regenerationPower)
    {
        super(gc, itemID, worldPosition);
        this.regenerationPower = regenerationPower;
    }

    public void applyRegenerationEffect(int regenerationPower)
    {
        Entity player = gc.player;
        int currentMana = player.statistics.getMana();
        int maxMana = player.statistics.getMaxMana();

        int newMana = currentMana + regenerationPower;
        if (newMana > maxMana)
        {
            newMana = maxMana;
        }

        player.statistics.setMana(newMana);
    }

    @Override
    public void consume(Entity entity)
    {
        //System.out.println("consumed mana potion " + regenerationPower);
        applyRegenerationEffect(regenerationPower);
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
    public void setItemSubType() {itemSubType = ItemSubType.MANA_POTION;}
}
