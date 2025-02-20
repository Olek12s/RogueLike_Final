package main.item.armor.boots;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.IronOre;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class IronBoots extends Boots
{
    public IronBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public IronBoots(GameController gc)
    {
        super(gc, ItemID.IRON_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(10);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new IronOre(gc), 2)), this);
    }
}
