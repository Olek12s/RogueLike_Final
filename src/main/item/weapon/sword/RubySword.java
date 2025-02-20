package main.item.weapon.sword;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Diamond;
import main.item.ingredients.Ruby;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class RubySword extends Sword
{
    public RubySword(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.RUBY_SWORD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public RubySword(GameController gc) {super(gc, ItemID.RUBY_SWORD);}

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Ruby Sword");
        statistics.setStackable(false);
        meleeAttackHeight = 30;
        statistics.setPhysicalDamage(60);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Ruby(gc), 3)), this);
    }


    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 2;
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*2);
    }
}
