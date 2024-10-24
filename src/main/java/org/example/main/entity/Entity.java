package org.example.main.entity;

import org.example.main.Drawable;
import org.example.main.GameController;
import org.example.main.Updatable;
import utilities.Position;

import java.awt.*;

public abstract class Entity implements Drawable, Updatable
{
    GameController gc;

    public Entity(GameController gc)
    {
        this.gc = gc;
    }



    abstract Position getPosition();
    abstract void setPosition(Position position);
    public abstract void update();
}
