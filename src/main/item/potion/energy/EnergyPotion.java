package main.item.potion.energy;

import main.controller.GameController;
import main.entity.Entity;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public abstract class EnergyPotion extends Item implements Consumable
{
    protected int regenerationPower;

    public EnergyPotion(GameController gc, ItemID itemID, int regenerationPower)
    {
        super(gc, itemID);
        this.gc = gc;
        this.regenerationPower = regenerationPower;
    }

    public EnergyPotion(GameController gc, ItemID itemID, Position worldPosition, int regenerationPower)
    {
        super(gc, itemID, worldPosition);
        this.regenerationPower = regenerationPower;
    }

    public void applyRegenerationEffect(int regenerationPower)
    {
        Entity player = gc.player;
        int currentEnergy = player.statistics.getStamina();
        int maxEnergy = player.statistics.getMaxStamina();

        int newEnergy = currentEnergy + regenerationPower;
        if (newEnergy > maxEnergy)
        {
            newEnergy = maxEnergy;
        }

        player.statistics.setStamina(newEnergy);
    }

    @Override
    public void consume(Entity entity)
    {
        //System.out.println("consumed energy potion " + regenerationPower);
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
    public void setItemSubType() {itemSubType = ItemSubType.ENERGY_POTION;}
}
