package main.item.armor.chestplate;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.IronOre;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class IronChestplate extends Chestplate
{
    public IronChestplate(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_CHESTPLATE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public IronChestplate(GameController gc)
    {
        super(gc, ItemID.IRON_CHESTPLATE);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Chestplate");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(17);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new IronOre(gc), 5)), this);
    }
}
