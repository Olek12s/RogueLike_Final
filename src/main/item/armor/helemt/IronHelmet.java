package main.item.armor.helemt;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.IronOre;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class IronHelmet extends Helmet
{
    public IronHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public IronHelmet(GameController gc)
    {
        super(gc, ItemID.IRON_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(11);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new IronOre(gc), 3)), this);
    }
}
