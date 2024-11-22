package main;

public enum DrawPriorities
{
    mapGrid(1),
    Entity(2),
    Player(3),
    Hitbox(4),
    Item(5),
    HUD(999);

    public final int value;

    DrawPriorities(int value) {this.value = value;}
}