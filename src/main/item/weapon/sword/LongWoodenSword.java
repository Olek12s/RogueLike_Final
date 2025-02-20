package main.item.weapon.sword;

import main.controller.GameController;
import main.item.Crafting;
import main.item.Item;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Wood;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class LongWoodenSword extends Sword
{
    public LongWoodenSword(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LONG_WOODEN_SWORD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public LongWoodenSword(GameController gc)
    {
        super(gc, ItemID.LONG_WOODEN_SWORD);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large Wooden Sword");
        statistics.setStackable(false);
        meleeAttackHeight = 45;
        statistics.setPhysicalDamage(8);
        statistics.setMovementSpeedPenalty(1.5f);
        attackRestTime = 10;
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 4)), this);
    }


    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 3;
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*3);
    }
}
