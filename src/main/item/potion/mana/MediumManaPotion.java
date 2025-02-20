package main.item.potion.mana;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.BlueFlower;
import utilities.Position;

import java.util.List;

public class MediumManaPotion extends ManaPotion
{
    public MediumManaPotion(GameController gc)
    {
        super(gc, ItemID.MEDIUM_MANA_POTION, 60);
    }

    public MediumManaPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.MEDIUM_MANA_POTION, worldPosition, 60);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Medium mana potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new BlueFlower(gc), 8)), this);
    }
}
