package main.inventory;

public enum SlotType
{
    mainInvSlot(0),
    beltSlot(1),
    helmetSlot(2),
    chestplateSlot(3),
    pantsSlot(4),
    bootsSlot(5),
    shieldSlot(6),
    ring1Slot(7),
    ring2Slot(8),
    amuletSlot(9);

    private final int id;

    SlotType(int id) {this.id = id;}

    public int getId() {return id;}

    public static SlotType fromId(int id)
    {
        for (SlotType slotType : values())
        {
            if (slotType.getId() == id)
            {
                return slotType;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }

    public static int getWidthMultipler(SlotType ID)
    {
        switch (ID)
        {
            case mainInvSlot: return 1;
            case beltSlot: return 1;
            case helmetSlot: return 2;
            case chestplateSlot: return 2;
            case pantsSlot: return 2;
            case bootsSlot: return 2;
            case shieldSlot: return 2;
            case ring1Slot: return 1;
            case ring2Slot: return 1;
            case amuletSlot: return 1;
            default: return 1;
        }
    }

    public static int getHeightMultipler(SlotType ID)
    {
        switch (ID)
        {
            case mainInvSlot: return 1;
            case beltSlot: return 1;
            case helmetSlot: return 2;
            case chestplateSlot: return 3;
            case pantsSlot: return 3;
            case bootsSlot: return 1;
            case shieldSlot: return 2;
            case ring1Slot: return 1;
            case ring2Slot: return 1;
            case amuletSlot: return 1;
            default: return 1;
        }
    }
}