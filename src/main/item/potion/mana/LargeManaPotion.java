package main.item.potion.mana;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.BlueFlower;
import utilities.Position;

import java.util.List;

public class LargeManaPotion extends ManaPotion
{
    public LargeManaPotion(GameController gc)
    {
        super(gc, ItemID.LARGE_MANA_POTION, 100);
    }

    public LargeManaPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LARGE_MANA_POTION, worldPosition, 100);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large mana potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new BlueFlower(gc), 12)), this);
    }
}
