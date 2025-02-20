package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class WoodenChestplate extends Chestplate
{
    public WoodenChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenChestplate(GameController gc)
    {
        super(gc, ItemID.WOODEN_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(6);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 5)), this);
    }
}
