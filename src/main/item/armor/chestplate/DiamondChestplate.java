package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Diamond;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class DiamondChestplate extends Chestplate
{
    public DiamondChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public DiamondChestplate(GameController gc)
    {
        super(gc, ItemID.DIAMOND_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(34);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Diamond(gc), 5)), this);
    }
}
