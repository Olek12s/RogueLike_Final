package world.map.tiles;

public class TileSide
{
    // 2^4 of combinations
    private final boolean up;
    private final boolean down;
    private final boolean left;
    private final boolean right;


    public boolean isUp() {return up;}
    public boolean isDown() {return down;}
    public boolean isLeft() {return left;}
    public boolean isRight() {return right;}

    public TileSide(boolean up, boolean down, boolean left, boolean right)
    {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
}
