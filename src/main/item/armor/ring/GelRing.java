package main.item.armor.ring;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Slime;
import utilities.Position;

import java.util.List;

public class GelRing extends Ring
{
    public GelRing(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.GEL_RING, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public GelRing(GameController gc)
    {
        super(gc, ItemID.GEL_RING);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Gel Ring");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setMagicalArmor(4);
        statistics.setArmor(2);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Slime(gc), 4)), this);
    }
}
