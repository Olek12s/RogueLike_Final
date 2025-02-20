package main.item.armor.shield;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Ruby;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class RubyShield extends Shield
{
    public RubyShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public RubyShield(GameController gc)
    {
        super(gc, ItemID.RUBY_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(100);
        statistics.setMovementSpeedPenalty(1.3f);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Ruby(gc), 4)), this);
    }
}
