package main.item.armor.helemt;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Diamond;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class DiamondHelmet extends Helmet
{
    public DiamondHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public DiamondHelmet(GameController gc)
    {
        super(gc, ItemID.DIAMOND_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(22);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Diamond(gc), 3)), this);
    }
}
