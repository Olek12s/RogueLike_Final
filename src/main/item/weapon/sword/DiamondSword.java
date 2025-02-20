package main.item.weapon.sword;

import main.controller.GameController;
import main.item.Crafting;
import main.item.ItemID;
import main.item.RecipeIngredient;
import main.item.ingredients.Diamond;
import main.item.ingredients.IronOre;
import utilities.Hitbox;
import utilities.Position;

import java.util.List;

public class DiamondSword extends Sword
{
    public DiamondSword(GameController gc, Position worldPosition)
    {
        super(gc, ItemID.DIAMOND_SWORD, worldPosition);
        this.worldPosition = worldPosition;
        this.setOnGround(true);
        setRecipe();
    }

    public DiamondSword(GameController gc) {super(gc, ItemID.DIAMOND_SWORD);}

    @Override
    public void setStatistics()
    {
        statistics.setItemName("Diamond Sword");
        statistics.setStackable(false);
        meleeAttackHeight = 30;
        statistics.setPhysicalDamage(25);
    }

    public void setRecipe()
    {
        recipe = Crafting.getOrCreate(List.of(
                new RecipeIngredient(new Diamond(gc), 3)), this);
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
