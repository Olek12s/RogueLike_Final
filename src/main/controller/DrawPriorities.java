package main.controller;

import ui.HUD;

public enum DrawPriorities
{
    mapGrid(1),
    ItemGround(2),
    Entity(3),
    Player(4),
    Hitbox(5),
    HUD(950),
    ItemInventory(951),
    Cursor(999);

    public final int value;

    DrawPriorities(int value) {this.value = value;}
}