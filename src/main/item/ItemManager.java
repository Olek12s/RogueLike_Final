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

        spriteMap.put(ItemID.WOODEN_HELMET, new Sprite(FileManipulation.loadImage("resources/items/armor/WoodenHelmet"), 32));
        spriteMap.put(ItemID.WOODEN_CHESTPLATE, new Sprite(FileManipulation.loadImage("resources/items/armor/WoodenChestplate"), 48));
        spriteMap.put(ItemID.WOODEN_PANTS, new Sprite(FileManipulation.loadImage("resources/items/armor/WoodenPants"), 48));
        spriteMap.put(ItemID.WOODEN_BOOTS, new Sprite(FileManipulation.loadImage("resources/items/armor/WoodenBoots"), 32));
        spriteMap.put(ItemID.GEL_RING, new Sprite(FileManipulation.loadImage("resources/items/armor/GelRing"), 16));
        spriteMap.put(ItemID.GEL_AMULET, new Sprite(FileManipulation.loadImage("resources/items/armor/GelAmulet"), 16));
        spriteMap.put(ItemID.WOODEN_SHIELD, new Sprite(FileManipulation.loadImage("resources/items/armor/WoodenShield"), 32));

        spriteMap.put(ItemID.WOODEN_PICKAXE, new Sprite(FileManipulation.loadImage("resources/items/tool/WoodenPickaxe"), 32));
    }
}
