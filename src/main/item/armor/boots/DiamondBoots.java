package main.item.armor.boots;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Diamond;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class DiamondBoots extends Boots
{
    public DiamondBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public DiamondBoots(GameController gc)
    {
        super(gc, ItemID.DIAMOND_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(20);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Diamond(gc), 2)), this);
    }
}
