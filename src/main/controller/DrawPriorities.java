package main.controller;

import ui.HUD;

public enum DrawPriorities
{
    mapGrid(1),
    Entity(2),
    Player(3),
    Hitbox(4),
    ItemGround(5),
    HUD(950),
    ItemInventory(951),
    Cursor(999);

    public final int value;

    DrawPriorities(int value) {this.value = value;}
}