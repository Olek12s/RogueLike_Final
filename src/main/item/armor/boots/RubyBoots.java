package main.item.armor.boots;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Ruby;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class RubyBoots extends Boots
{
    public RubyBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public RubyBoots(GameController gc)
    {
        super(gc, ItemID.RUBY_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(40);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Ruby(gc), 2)), this);
    }
}
