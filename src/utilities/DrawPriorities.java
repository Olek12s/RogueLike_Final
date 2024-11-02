package utilities;

public enum DrawPriorities
{
    Tile(0),
    Entity(1);

    public final int value;

    DrawPriorities(int value) {this.value = value;}
}
