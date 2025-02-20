package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;
import main.item.Crafting;
import main.item.RecipeIngredient;
import main.item.ingredients.RedFlower;
import utilities.Position;

import java.util.List;

public class SmallHealthPotion extends HealthPotion
{
    public SmallHealthPotion(GameController gc)
    {
        super(gc, ItemID.SMALL_HP_POTION, 30);
    }

    public SmallHealthPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.SMALL_HP_POTION, worldPosition,30);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Small health potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new RedFlower(gc), 4)), this);
    }
}
