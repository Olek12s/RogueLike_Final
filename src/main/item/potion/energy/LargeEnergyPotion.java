package main.item.potion.energy;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.YellowFlower;
import utilities.Position;

import java.util.List;

public class LargeEnergyPotion extends EnergyPotion
{
    public LargeEnergyPotion(GameController gc)
    {
        super(gc, ItemID.LARGE_ENERGY_POTION, 100);
    }

    public LargeEnergyPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LARGE_ENERGY_POTION, worldPosition, 100);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large energy potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new YellowFlower(gc), 12)), this);
    }
}
