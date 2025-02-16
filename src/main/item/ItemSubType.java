package main.item;

public enum ItemSubType
{
    AMULET(0),
    BOOTS(1),
    CHESTPLATE(2),
    HELMET(3),
    PANTS(4),
    RING(5),
    SHIELD(6),

    ENERGY_POTION(7),
    HEALTH_POTION(8),
    MANA_POTION(9),

    PICKAXE(10),
    SWORD(11);

    private final int id;

    ItemSubType (int id) {this.id = id;}

    public int getId() {return id;}

    public static ItemSubType fromId(int id)
    {
        for (ItemSubType  itemSubType : values())
        {
            if (itemSubType.getId() == id)
            {
                return itemSubType;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
