package world.map;

import world.map.tiles.TileManager;

public enum MapLevels
{
    CAVE_NEGATIVE_TWO(-2),
    CAVE_NEGATIVE_ONE(-1),
    SURFACE(0);

    private final int value;

    MapLevels(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static MapLevels fromId(int id)
    {
        for (MapLevels mapLevel : values())
        {
            if (mapLevel.getValue() == id)
            {
                return mapLevel;
            }
        }
        throw new IllegalArgumentException("Illegal ID: " + id);
    }
}