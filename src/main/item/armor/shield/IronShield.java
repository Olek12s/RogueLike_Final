package main.item.armor.shield;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.IronOre;
import main.item.ingredients.Wood;
import utilities.Position;

import java.util.List;

public class IronShield extends Shield
{
    public IronShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.IRON_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public IronShield(GameController gc)
    {
        super(gc, ItemID.IRON_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Iron Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(25);
        statistics.setMovementSpeedPenalty(1.3f);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new IronOre(gc), 4)), this);
    }
}
