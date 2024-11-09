package utilities;

public enum DrawPriorities
{
    mapGrid(0),
    Entity(1),
    Hitbox(2),
    Item(3);

    public final int value;

    DrawPriorities(int value) {this.value = value;}
}
