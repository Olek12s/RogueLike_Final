package main.item.armor.shield;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.ItemRenderer;
import main.item.RecipeIngredient;
import main.item.armor.shield.Shield;
import main.item.ingredients.Wood;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class WoodenShield extends Shield
{
    public WoodenShield(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.WOODEN_SHIELD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public WoodenShield(GameController gc)
    {
        super(gc, ItemID.WOODEN_SHIELD);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Wooden Shield");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setArmor(10);
        statistics.setMovementSpeedPenalty(1.3f);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Wood(gc), 4)), this);
    }
}
