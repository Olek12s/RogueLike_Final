package main.item;

public enum ItemID
{
    SMALL_HP_POTION(0),
    MEDIUM_HP_POTION(1),
    LARGE_HP_POTION(2),

    SMALL_ENERGY_POTION(3),
    MEDIUM_ENERGY_POTION(4),
    LARGE_ENERGY_POTION(5),

    SMALL_MANA_POTION(6),
    MEDIUM_MANA_POTION(7),
    LARGE_MANA_POTION(8),

    WOODEN_SHIELD(9),
    WOODEN_BOOTS(10),
    WOODEN_PANTS(11),
    WOODEN_CHESTPLATE(12),
    WOODEN_HELMET(13),
    GEL_AMULET(14),
    GEL_RING(15),

    WOODEN_PICKAXE(16),
    WOODEN_AXE(17),
    WOODEN_SWORD(18),
    LONG_WOODEN_SWORD(19),

    BITING_SLIME_WEAPON(20),
    SPIDER_WEAPON(21),
    ZOMBIE_WEAPON(22),
    HANDS_WEAPON(23),

    RED_FLOWER(24),
    YELLOW_FLOWER(25),
    BLUE_FLOWER(26);

    private final int id;

    ItemID(int id) {this.id = id;}

    public int getId() {return id;}

    public static ItemID fromId(int id)
    {
        for (ItemID itemID : values())
        {
            if (itemID.getId() == id)
            {
                return itemID;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
