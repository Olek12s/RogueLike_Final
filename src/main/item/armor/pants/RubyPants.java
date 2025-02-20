package main.item.armor.pants;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Ruby;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class RubyPants extends Pants
{
    public RubyPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public RubyPants(GameController gc)
    {
        super(gc, ItemID.RUBY_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(60);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Ruby(gc), 4)), this);
    }
}
