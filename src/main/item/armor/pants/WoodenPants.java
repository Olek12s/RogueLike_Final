package main.item.armor.pants;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class WoodenPants extends Pants
{
    public WoodenPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenPants(GameController gc)
    {
        super(gc, ItemID.WOODEN_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(5);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 4)), this);
    }
}
