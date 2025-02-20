package main.item.armor.amulet;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.ItemRenderer;
import main.item.RecipeIngredient;
import main.item.ingredients.RedFlower;
import main.item.ingredients.Slime;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class GelAmulet extends Amulet
{
    public GelAmulet(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.GEL_AMULET, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public GelAmulet(GameController gc)
    {
        super(gc, ItemID.GEL_AMULET);
    }


    @Override
    public void setStatistics()
    {
        statistics.setItemName("Gel Amulet");
        statistics.setStackable(false);
        statistics.setStackSize(1);
        statistics.setMagicalArmor(7);
        statistics.setArmor(4);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Slime(gc), 6)), this);
    }
}
