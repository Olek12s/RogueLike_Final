package main.item.tool.axe;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.BlueFlower;
import utilities.Position;

import java.util.List;

public class WoodenAxe extends Axe
{

    public WoodenAxe(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_AXE, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenAxe(GameController gc)
    {
        super(gc, ItemID.WOODEN_AXE);
    }

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Axe");
        statistics.setStackable(false);
        meleeAttackHeight = 30;
        attackRestTime = 20;
        statistics.setPhysicalDamage(2);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new BlueFlower(gc), 2)), this);
    }
}
