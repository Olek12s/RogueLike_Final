package main.item.potion.health;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.RedFlower;
import utilities.Position;

import java.util.List;

public class MediumHealthPotion extends HealthPotion
{
    public MediumHealthPotion(GameController gc)
    {
        super(gc, ItemID.MEDIUM_HP_POTION, 60);
    }

    public MediumHealthPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.MEDIUM_HP_POTION, worldPosition, 60);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Medium health potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new RedFlower(gc), 8)), this);
    }
}
