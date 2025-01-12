package world.map.tiles;

public enum EdgeCode
{
    NONE(0),       // No edges
    UP(1),       // UP
    DOWN(2),       // DOWN
    UP_DOWN(3),       // UP-DOWN
    LEFT(4),       // LEFT
    LEFT_UP(5),       // LEFT-UP
    LEFT_DOWN(6),       // LEFT-DOWN
    UP_DOWN_LEFT(7),       // UP-DOWN-LEFT
    RIGHT(8),       // RIGHT
    UP_RIGHT(9),       // UP-RIGHT
    DOWN_RIGHT(10),      // DOWN-RIGHT
    UP_DOWN_RIGHT(11),      // UP-DOWN-RIGHT
    LEFT_RIGHT(12),      // LEFT-RIGHT
    UP_LEFT_RIGHT(13),      // UP-LEFT-RIGHT
    DOWN_LEFT_RIGHT(14),      // DOWN-LEFT-RIGHT
    UP_DOWN_LEFT_RIGHT(15);      // ALL edges

    private final int id;

    EdgeCode(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public static EdgeCode fromId(int id) {
        for (EdgeCode code : values()) {
            if (code.id == id) {
                return code;
            }
        }
        throw new IllegalArgumentException("Invalid EdgeCode ID: " + id);
    }
}
