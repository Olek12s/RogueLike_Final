package main.item.armor.helemt;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class WoodenHelmet extends Helmet
{
    public WoodenHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenHelmet(GameController gc)
    {
        super(gc, ItemID.WOODEN_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(4);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 3)), this);
    }
}
