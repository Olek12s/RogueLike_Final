package world.map.tiles;

public enum TileSide
{
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    private final int id;

    TileSide(int id) {this.id = id;}

    public int getId() {return id;}

    public static TileSide fromId(int id)
    {
        for (TileSide tileSide : values())
        {
            if (tileSide.getId() == id)
            {
                return tileSide;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
