package main.item.tool.pickaxe;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.BlueFlower;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class WoodenPickaxe extends Pickaxe
{

    public WoodenPickaxe(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_PICKAXE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenPickaxe(GameController gc)
    {
        super(gc, ItemID.WOODEN_PICKAXE);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Pickaxe");
        statistics.setStackable(false);
        meleeAttackHeight = 30;
        statistics.setPhysicalDamage(3);
        attackRestTime = 20;
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 2)), this);
    }
}