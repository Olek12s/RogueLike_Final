package main.item.armor.boots;

import main.controller.GameController;
import main.item.*;
import main.item.ingredients.Slime;
import main.item.ingredients.Wood;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class WoodenBoots extends Boots
{
    public WoodenBoots(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_BOOTS, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenBoots(GameController gc)
    {
        super(gc, ItemID.WOODEN_BOOTS);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Boots");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(3);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 2)), this);
    }
}