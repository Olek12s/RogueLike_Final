package main.item.potion.health;

import main.controller.GameController;
import main.item.ItemID;
import main.item.Recipe;
import main.item.RecipeIngredient;
import main.item.ingredients.RedFlower;
import main.item.potion.energy.SmallEnergyPotion;
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
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Small health potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = new Recipe(List.of(
                new RecipeIngredient(new RedFlower(gc), 4)), this);
    }
}
