package main;

public enum DrawPriorities
{
    mapGrid(0),
    Entity(1),
    Player(2),
    Hitbox(3),
    Item(4);

    public final int value;

    DrawPriorities(int value) {this.value = value;}
}