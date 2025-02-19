package main.item.potion.mana;

import main.controller.GameController;
import main.item.ItemID;
import main.item.Crafting;
import main.item.RecipeIngredient;
import main.item.ingredients.BlueFlower;
import utilities.Position;

import java.util.List;

public class SmallManaPotion extends ManaPotion
{
    public SmallManaPotion(GameController gc)
    {
        super(gc, ItemID.SMALL_MANA_POTION, 30);
    }

    public SmallManaPotion(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.SMALL_MANA_POTION, worldPosition, 30);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Small mana potion");
        statistics.setStackable(false);
        statistics.setStackSize(1);
    }

    private void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new BlueFlower(gc), 4)), this);
    }
}
