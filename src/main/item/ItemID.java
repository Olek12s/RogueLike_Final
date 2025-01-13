package main.item;

import world.map.tiles.TileID;

public enum ItemID
{
    SMALL_HP_POTION(0),
    MEDIUM_HP_POTION(1),
    LARGE_HP_POTION(2),

    SMALL_ENERGY_POTION(3),
    MEDIUM_ENERGY_POTION(4),
    BIG_ENERGY_POTION(5),

    SMALL_MANA_POTION(6),
    MEDIUM_MANA_POTION(7),
    LARGE_MANA_POTION(8);

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
