package main.item;

import main.controller.GameController;
import utilities.FileManipulation;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

public class ItemManager
{
    private static Map<ItemID, Sprite> spriteMap;
    GameController gc;

    public ItemManager(GameController gc)
    {
        this.gc = gc;
        spriteMap = new HashMap<>();
        initializeSprites();
    }

    public static Sprite getItemSprite(ItemID itemID)
    {
        return spriteMap.get(itemID);
    }

    public void initializeSprites()
    {
        spriteMap.put(ItemID.SMALL_HP_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/SmallHealthPotion"), 16));
        spriteMap.put(ItemID.MEDIUM_HP_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/MediumHealthPotion"), 16));
        spriteMap.put(ItemID.LARGE_HP_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/LargeHealthPotion"), 16));

        spriteMap.put(ItemID.SMALL_MANA_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/SmallManaPotion"), 16));
        spriteMap.put(ItemID.MEDIUM_MANA_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/MediumManaPotion"), 16));
        spriteMap.put(ItemID.LARGE_MANA_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/LargeManaPotion"), 16));

        spriteMap.put(ItemID.SMALL_ENERGY_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/SmallEnergyPotion"), 16));
        spriteMap.put(ItemID.MEDIUM_ENERGY_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/MediumEnergyPotion"), 16));
        spriteMap.put(ItemID.LARGE_ENERGY_POTION, new Sprite(FileManipulation.loadImage("resources/items/potions/LargeEnergyPotion"), 16));

        spriteMap.put(ItemID.GEL_RING, new Sprite(FileManipulation.loadImage("resources/items/armor/GelRing"), 16));
        spriteMap.put(ItemID.GEL_AMULET, new Sprite(FileManipulation.loadImage("resources/items/armor/GelAmulet"), 16));

        spriteMap.put(ItemID.WOODEN_HELMET, new Sprite(FileManipulation.loadImage("resources/items/armor/wooden/WoodenHelmet"), 32));
        spriteMap.put(ItemID.WOODEN_CHESTPLATE, new Sprite(FileManipulation.loadImage("resources/items/armor/wooden/WoodenChestplate"), 48));
        spriteMap.put(ItemID.WOODEN_PANTS, new Sprite(FileManipulation.loadImage("resources/items/armor/wooden/WoodenPants"), 48));
        spriteMap.put(ItemID.WOODEN_BOOTS, new Sprite(FileManipulation.loadImage("resources/items/armor/wooden/WoodenBoots"), 32));
        spriteMap.put(ItemID.WOODEN_SHIELD, new Sprite(FileManipulation.loadImage("resources/items/armor/wooden/WoodenShield"), 32));

        spriteMap.put(ItemID.IRON_HELMET, new Sprite(FileManipulation.loadImage("resources/items/armor/iron/IronHelmet"), 32));
        spriteMap.put(ItemID.IRON_CHESTPLATE, new Sprite(FileManipulation.loadImage("resources/items/armor/iron/IronChestplate"), 48));
        spriteMap.put(ItemID.IRON_PANTS, new Sprite(FileManipulation.loadImage("resources/items/armor/iron/IronPants"), 48));
        spriteMap.put(ItemID.IRON_BOOTS, new Sprite(FileManipulation.loadImage("resources/items/armor/iron/IronBoots"), 32));
        spriteMap.put(ItemID.IRON_SHIELD, new Sprite(FileManipulation.loadImage("resources/items/armor/iron/IronShield"), 32));

        spriteMap.put(ItemID.DIAMOND_HELMET, new Sprite(FileManipulation.loadImage("resources/items/armor/diamond/DiamondHelmet"), 32));
        spriteMap.put(ItemID.DIAMOND_CHESTPLATE, new Sprite(FileManipulation.loadImage("resources/items/armor/diamond/DiamondChestplate"), 48));
        spriteMap.put(ItemID.DIAMOND_PANTS, new Sprite(FileManipulation.loadImage("resources/items/armor/diamond/DiamondPants"), 48));
        spriteMap.put(ItemID.DIAMOND_BOOTS, new Sprite(FileManipulation.loadImage("resources/items/armor/diamond/DiamondBoots"), 32));
        spriteMap.put(ItemID.DIAMOND_SHIELD, new Sprite(FileManipulation.loadImage("resources/items/armor/diamond/DiamondShield"), 32));

        spriteMap.put(ItemID.RUBY_HELMET, new Sprite(FileManipulation.loadImage("resources/items/armor/ruby/RubyHelmet"), 32));
        spriteMap.put(ItemID.RUBY_CHESTPLATE, new Sprite(FileManipulation.loadImage("resources/items/armor/ruby/RubyChestplate"), 48));
        spriteMap.put(ItemID.RUBY_PANTS, new Sprite(FileManipulation.loadImage("resources/items/armor/ruby/RubyPants"), 48));
        spriteMap.put(ItemID.RUBY_BOOTS, new Sprite(FileManipulation.loadImage("resources/items/armor/ruby/RubyBoots"), 32));
        spriteMap.put(ItemID.RUBY_SHIELD, new Sprite(FileManipulation.loadImage("resources/items/armor/ruby/RubyShield"), 32));

        spriteMap.put(ItemID.WOODEN_PICKAXE, new Sprite(FileManipulation.loadImage("resources/items/tool/WoodenPickaxe"), 32));
        spriteMap.put(ItemID.WOODEN_AXE, new Sprite(FileManipulation.loadImage("resources/items/tool/WoodenAxe"), 32));


        spriteMap.put(ItemID.WOODEN_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/WoodenSword"), 32));
        spriteMap.put(ItemID.LONG_WOODEN_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/LongWoodenSword"), 48));
        spriteMap.put(ItemID.IRON_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/IronSword"), 32));
        spriteMap.put(ItemID.LONG_IRON_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/LongIronSword"), 48));
        spriteMap.put(ItemID.DIAMOND_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/DiamondSword"), 32));
        spriteMap.put(ItemID.LONG_DIAMOND_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/LongDiamondSword"), 48));
        spriteMap.put(ItemID.RUBY_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/RubySword"), 32));
        spriteMap.put(ItemID.LONG_RUBY_SWORD, new Sprite(FileManipulation.loadImage("resources/items/weapon/LongRubySword"), 48));

        spriteMap.put(ItemID.HANDS_WEAPON, new Sprite(FileManipulation.loadImage("resources/items/weapon/MobWeapon"), 16));
        spriteMap.put(ItemID.BITING_SLIME_WEAPON, new Sprite(FileManipulation.loadImage("resources/items/weapon/MobWeapon"), 16));
        spriteMap.put(ItemID.SPIDER_WEAPON, new Sprite(FileManipulation.loadImage("resources/items/weapon/MobWeapon"), 16));
        spriteMap.put(ItemID.ZOMBIE_WEAPON, new Sprite(FileManipulation.loadImage("resources/items/weapon/MobWeapon"), 16));

        spriteMap.put(ItemID.RED_FLOWER, new Sprite(FileManipulation.loadImage("resources/items/ingredients/RedFlower"), 16));
        spriteMap.put(ItemID.BLUE_FLOWER, new Sprite(FileManipulation.loadImage("resources/items/ingredients/BlueFlower"), 16));
        spriteMap.put(ItemID.YELLOW_FLOWER, new Sprite(FileManipulation.loadImage("resources/items/ingredients/YellowFlower"), 16));
        spriteMap.put(ItemID.WOOD, new Sprite(FileManipulation.loadImage("resources/items/ingredients/Wood"), 16));
        spriteMap.put(ItemID.COAL, new Sprite(FileManipulation.loadImage("resources/items/ingredients/Coal"), 16));
        spriteMap.put(ItemID.IRON_ORE, new Sprite(FileManipulation.loadImage("resources/items/ingredients/IronOre"), 16));
        spriteMap.put(ItemID.DIAMOND, new Sprite(FileManipulation.loadImage("resources/items/ingredients/Diamond"), 16));
        spriteMap.put(ItemID.RUBY, new Sprite(FileManipulation.loadImage("resources/items/ingredients/Ruby"), 16));
        spriteMap.put(ItemID.SLIME, new Sprite(FileManipulation.loadImage("resources/items/ingredients/Slime"), 16));
    }
}
