package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Ruby;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class RubyChestplate extends Chestplate
{
    public RubyChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public RubyChestplate(GameController gc)
    {
        super(gc, ItemID.RUBY_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(68);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Ruby(gc), 5)), this);
    }
}
