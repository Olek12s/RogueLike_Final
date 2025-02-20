package main.item.armor.shield;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Diamond;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class DiamondShield extends Shield
{
    public DiamondShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public DiamondShield(GameController gc)
    {
        super(gc, ItemID.DIAMOND_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(50);
        statistics.setMovementSpeedPenalty(1.3f);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Diamond(gc), 4)), this);
    }
}
