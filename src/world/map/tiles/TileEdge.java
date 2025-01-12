package world.map.tiles;

public enum TileEdge
{
    WATER(0),
    STONE(1);

    private final int id;

    TileEdge(int id) {this.id = id;}

    public int getId() {return id;}

    public static TileEdge fromId(int id)
    {
        for (TileEdge tileEdge : values())
        {
            if (tileEdge.getId() == id)
            {
                return tileEdge;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
