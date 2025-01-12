package world.map.tiles;

public enum TileID
{
    DEFAULT_TILE(-2),
    DEFAULT_TILE_COLLISION(-1),
    DIRT(0),
    GRASS(1),
    STONE(2),
    ROCK(3),
    SAND(4),
    WATER(5),
    CAVE_ENTRANCE(6),
    CAVE_DEEP_ENTRANCE(7),
    CAVE_RUINS_ENTRANCE(8),
    CAVE_EXIT(9),
    CAVE_DEEP_EXIT(10),
    CAVE_RUINS_EXIT(11),
    CAVE_FLOOR(12),
    GRAVEL(13),
    BASALT_FLOOR(14);

    private final int id;

    TileID(int id) {this.id = id;}

    public int getId() {return id;}

    public static TileID fromId(int id)
    {
        for (TileID tileID : values())
        {
            if (tileID.getId() == id)
            {
                return tileID;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
