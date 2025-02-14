package main.item;

public enum ItemType
{
    ARMOR(0),
    POTION(1),
    TOOL(2),
    WEAPON(3);

    private final int id;

    ItemType (int id) {this.id = id;}

    public int getId() {return id;}

    public static ItemType fromId(int id)
    {
        for (ItemType  itemType : values())
        {
            if (itemType.getId() == id)
            {
                return itemType;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
