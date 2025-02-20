package main.item.armor.pants;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.IronOre;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class IronPants extends Pants
{
    public IronPants(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_PANTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public IronPants(GameController gc)
    {
        super(gc, ItemID.IRON_PANTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Pants");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(15);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new IronOre(gc), 4)), this);
    }
}
