package main.item.armor.helemt;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Ruby;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class RubyHelmet extends Helmet
{
    public RubyHelmet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_HELMET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public RubyHelmet(GameController gc)
    {
        super(gc, ItemID.RUBY_HELMET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Helmet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(44);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Ruby(gc), 3)), this);
    }
}
