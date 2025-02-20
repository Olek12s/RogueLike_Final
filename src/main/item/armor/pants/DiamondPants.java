package main.item.armor.pants;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Diamond;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class DiamondPants extends Pants
{
    public DiamondPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public DiamondPants(GameController gc)
    {
        super(gc, ItemID.DIAMOND_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(30);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Diamond(gc), 4)), this);
    }
}
