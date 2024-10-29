package main.entity;


import main.Drawable;
import main.GameController;
import main.Updatable;
import utilities.Position;

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
