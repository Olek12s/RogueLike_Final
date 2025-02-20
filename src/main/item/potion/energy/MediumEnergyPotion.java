package main.item.potion.energy;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.YellowFlower;
import utilities.Position;

import java.util.List;

public class MediumEnergyPotion extends EnergyPotion
{
    public MediumEnergyPotion(GameController gc)
    {
        super(gc, ItemID.MEDIUM_ENERGY_POTION, 60);
    }

    public MediumEnergyPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.MEDIUM_ENERGY_POTION, worldPosition, 60);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Medium energy potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new YellowFlower(gc), 8)), this);
    }
}
