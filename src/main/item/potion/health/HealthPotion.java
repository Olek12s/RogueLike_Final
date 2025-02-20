package main.item.potion.health;

import main.controller.GameController;
import main.entity.Entity;
import main.entity.player.Player;
import main.item.*;
import utilities.Hitbox;
import utilities.Position;

public abstract class HealthPotion extends Item implements Consumable
{
    protected int regenerationPower;

    public HealthPotion(GameController gc, ItemID itemID, int regenerationPower)
    {
        super(gc, itemID);
        this.gc = gc;
        this.regenerationPower = regenerationPower;
    }

    public HealthPotion(GameController gc, ItemID itemID, Position worldPosition, int regenerationPower)
    {
        super(gc, itemID, worldPosition);
        this.regenerationPower = regenerationPower;
    }

    public void applyRegenerationEffect(int regenerationPower)
    {
        Entity player = gc.player;
        int currentHP = player.statistics.getHitPoints();
        int maxHP = player.statistics.getMaxHitPoints();

        int newHP = currentHP + regenerationPower;
        if (newHP > maxHP)
        {
            newHP = maxHP;
        }

        player.statistics.setHitPoints(newHP);
    }

    @Override
    public void consume(Entity entity)
    {
        //System.out.println("consumed health potion " + regenerationPower);
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
    public void setItemSubType() {itemSubType = ItemSubType.HEALTH_POTION;}
}
