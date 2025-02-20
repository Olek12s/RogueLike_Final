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

public class WoodenSword extends Sword
{
    public WoodenSword(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_SWORD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenSword(GameController gc)
    {
        super(gc, ItemID.WOODEN_SWORD);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Sword");
        statistics.setStackable(false);
        meleeAttackHeight = 30;
        statistics.setPhysicalDamage(5);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 3)), this);
    }


    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 2;
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*2);
    }
}
