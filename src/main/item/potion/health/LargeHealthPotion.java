package main.item.potion.health;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.RedFlower;
import utilities.Position;

import java.util.List;

public class LargeHealthPotion extends HealthPotion
{
    public LargeHealthPotion(GameController gc)
    {
        super(gc, ItemID.LARGE_HP_POTION, 100);
    }

    public LargeHealthPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LARGE_HP_POTION, worldPosition, 100);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large health potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new RedFlower(gc), 12)), this);
    }
}
