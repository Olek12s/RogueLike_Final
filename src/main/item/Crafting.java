package main.item;

import main.controller.GameController;
import main.entity.Entity;
import main.entity.player.Player;
import main.inventory.Inventory;
import main.item.armor.amulet.GelAmulet;
import main.item.armor.shield.*;
import main.item.armor.boots.*;
import main.item.armor.boots.*;
import main.item.armor.pants.*;
import main.item.armor.chestplate.*;
import main.item.armor.helemt.*;
import main.item.armor.ring.GelRing;
import main.item.potion.energy.LargeEnergyPotion;
import main.item.potion.energy.MediumEnergyPotion;
import main.item.potion.energy.SmallEnergyPotion;
import main.item.potion.health.LargeHealthPotion;
import main.item.potion.health.MediumHealthPotion;
import main.item.potion.health.SmallHealthPotion;
import main.item.potion.mana.LargeManaPotion;
import main.item.potion.mana.MediumManaPotion;
import main.item.potion.mana.SmallManaPotion;
import main.item.tool.pickaxe.WoodenPickaxe;
import main.item.tool.axe.WoodenAxe;
import main.item.weapon.sword.*;
import utilities.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crafting
{
    public static final Map<ItemID, Crafting> craftings = new HashMap<>();
    private final List<RecipeIngredient> ingredients;
    private final Item result;

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }
    public Item getResultItem() {
        return result;
    }

    private Crafting(List<RecipeIngredient> ingredients, Item result)
    {
        this.ingredients = new ArrayList<>(ingredients);
        this.result = result;
    }

    public static Crafting getOrCreate(List<RecipeIngredient> ingredients, Item result)
    {
        return craftings.computeIfAbsent(result.getItemID(), key -> new Crafting(ingredients, result));
    }

    public boolean canCraft(Inventory inventory)
    {
        for (RecipeIngredient ingredient : ingredients)
        {
            int requiredQuantity = ingredient.getQuantity();
            int availableQuantity = 0;

            for (Item item : inventory.getInventoryItemList())
            {
                if (item.getItemID().equals(ingredient.getItem().getItemID()))
                {
                    availableQuantity++;
                    if (availableQuantity >= requiredQuantity)
                    {
                        break;
                    }
                }
            }

            if (availableQuantity < requiredQuantity)
            {
                return false;
            }
        }
        return true;
    }

    public void craft(Inventory inventory, Entity entity)
    {
        if (!canCraft(inventory))
        {
            return;
        }

        for (RecipeIngredient ingredient : ingredients)
        {
            int quantityToRemove = ingredient.getQuantity();

            for (int i = 0; i < inventory.getInventoryItemList().size() && quantityToRemove > 0; i++)
            {
                Item item = inventory.getInventoryItemList().get(i);
                if (item.getItemID().equals(ingredient.getItem().getItemID()))
                {
                    inventory.removeItemFromMainInv(item);
                    quantityToRemove--;
                    i--;
                }
            }
        }

        boolean addedToInventory = inventory.addItem(result);
        if (!addedToInventory)
        {
            entity.getInventory().dropItemOnGround(result);
        }
    }

    public static void initRecipes(GameController gc)   // very bad method
    {
        // Swords
        WoodenSword woodenSword = new WoodenSword(gc, new Position(0, 0));
        woodenSword.setOnGround(false);
        LongWoodenSword longWoodenSword = new LongWoodenSword(gc, new Position(0, 0));
        longWoodenSword.setOnGround(false);
        IronSword ironSword = new IronSword(gc, new Position(0, 0));
        ironSword.setOnGround(false);
        LongIronSword longIronSword = new LongIronSword(gc, new Position(0, 0));
        longIronSword.setOnGround(false);
        DiamondSword diamondSword = new DiamondSword(gc, new Position(0, 0));
        diamondSword.setOnGround(false);
        LongDiamondSword longDiamondSword = new LongDiamondSword(gc, new Position(0, 0));
        longDiamondSword.setOnGround(false);
        RubySword rubySword = new RubySword(gc, new Position(0, 0));
        rubySword.setOnGround(false);
        LongRubySword longRubySword = new LongRubySword(gc, new Position(0, 0));
        longRubySword.setOnGround(false);

        // HP
        SmallHealthPotion smallHpPotion = new SmallHealthPotion(gc, new Position(0, 0));
        smallHpPotion.setOnGround(false);
        MediumHealthPotion mediumHpPotion = new MediumHealthPotion(gc, new Position(0, 0));
        mediumHpPotion.setOnGround(false);
        LargeHealthPotion largeHpPotion = new LargeHealthPotion(gc, new Position(0, 0));
        largeHpPotion.setOnGround(false);

        // Energy
        SmallEnergyPotion smallEnergyPotion = new SmallEnergyPotion(gc, new Position(0, 0));
        smallEnergyPotion.setOnGround(false);
        MediumEnergyPotion mediumEnergyPotion = new MediumEnergyPotion(gc, new Position(0, 0));
        mediumEnergyPotion.setOnGround(false);
        LargeEnergyPotion largeEnergyPotion = new LargeEnergyPotion(gc, new Position(0, 0));
        largeEnergyPotion.setOnGround(false);

        // Mana
        SmallManaPotion smallManaPotion = new SmallManaPotion(gc, new Position(0, 0));
        smallManaPotion.setOnGround(false);
        MediumManaPotion mediumManaPotion = new MediumManaPotion(gc, new Position(0, 0));
        mediumManaPotion.setOnGround(false);
        LargeManaPotion largeManaPotion = new LargeManaPotion(gc, new Position(0, 0));
        largeManaPotion.setOnGround(false);

        // Wooden Armor
        WoodenShield woodenShield = new WoodenShield(gc, new Position(0, 0));
        woodenShield.setOnGround(false);
        WoodenBoots woodenBoots = new WoodenBoots(gc, new Position(0, 0));
        woodenBoots.setOnGround(false);
        WoodenPants woodenPants = new WoodenPants(gc, new Position(0, 0));
        woodenPants.setOnGround(false);
        WoodenChestplate woodenChestplate = new WoodenChestplate(gc, new Position(0, 0));
        woodenChestplate.setOnGround(false);
        WoodenHelmet woodenHelmet = new WoodenHelmet(gc, new Position(0, 0));
        woodenHelmet.setOnGround(false);

        // special items
        GelAmulet gelAmulet = new GelAmulet(gc, new Position(0, 0));
        gelAmulet.setOnGround(false);
        GelRing gelRing = new GelRing(gc, new Position(0, 0));
        gelRing.setOnGround(false);

        // Wooden tools
        WoodenPickaxe woodenPickaxe = new WoodenPickaxe(gc, new Position(0, 0));
        woodenPickaxe.setOnGround(false);
        WoodenAxe woodenAxe = new WoodenAxe(gc, new Position(0, 0));
        woodenAxe.setOnGround(false);

        // Iron Armor
        IronShield ironShield = new IronShield(gc, new Position(0, 0));
        ironShield.setOnGround(false);
        IronBoots ironBoots = new IronBoots(gc, new Position(0, 0));
        ironBoots.setOnGround(false);
        IronPants ironPants = new IronPants(gc, new Position(0, 0));
        ironPants.setOnGround(false);
        IronChestplate ironChestplate = new IronChestplate(gc, new Position(0, 0));
        ironChestplate.setOnGround(false);
        IronHelmet ironHelmet = new IronHelmet(gc, new Position(0, 0));
        ironHelmet.setOnGround(false);

        // Diamond Armor
        DiamondShield diamondShield = new DiamondShield(gc, new Position(0, 0));
        diamondShield.setOnGround(false);
        DiamondBoots diamondBoots = new DiamondBoots(gc, new Position(0, 0));
        diamondBoots.setOnGround(false);
        DiamondPants diamondPants = new DiamondPants(gc, new Position(0, 0));
        diamondPants.setOnGround(false);
        DiamondChestplate diamondChestplate = new DiamondChestplate(gc, new Position(0, 0));
        diamondChestplate.setOnGround(false);
        DiamondHelmet diamondHelmet = new DiamondHelmet(gc, new Position(0, 0));
        diamondHelmet.setOnGround(false);

        // Ruby armor
        RubyShield rubyShield = new RubyShield(gc, new Position(0, 0));
        rubyShield.setOnGround(false);
        RubyBoots rubyBoots = new RubyBoots(gc, new Position(0, 0));
        rubyBoots.setOnGround(false);
        RubyPants rubyPants = new RubyPants(gc, new Position(0, 0));
        rubyPants.setOnGround(false);
        RubyChestplate rubyChestplate = new RubyChestplate(gc, new Position(0, 0));
        rubyChestplate.setOnGround(false);
        RubyHelmet rubyHelmet = new RubyHelmet(gc, new Position(0, 0));
        rubyHelmet.setOnGround(false);
    }
}
