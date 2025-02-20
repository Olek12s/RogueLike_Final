package main.item.weapon.sword;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Ruby;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class LongRubySword extends Sword
{
    public LongRubySword(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.LONG_RUBY_SWORD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public LongRubySword(GameController gc) {super(gc, ItemID.LONG_RUBY_SWORD);}

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Large Ruby Sword");
        statistics.setStackable(false);
        meleeAttackHeight = 45;
        statistics.setPhysicalDamage(90);
        statistics.setMovementSpeedPenalty(1.5f);
        attackRestTime = 10;
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Ruby(gc), 4)), this);
    }


    @Override
    public void setSlotWidth()
    {
        slotWidth = 1;
    }

    @Override
    public void setSlotHeight()
    {
        slotHeight = 3;
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, slotPixelSize*1, slotPixelSize*3);
    }
}
