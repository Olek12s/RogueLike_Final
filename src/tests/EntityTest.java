package tests;

import main.GameController;
import main.entity.Entity;
import test.annotations.Before;
import test.annotations.Test;
import test.runner.Assertions;
import utilities.Position;

public class EntityTest
{
    GameController gc;
    Entity entity;

    @Before
    public void initialize()
    {
        gc = new GameController();
        entity = new Entity(gc, new Position(0, 0));
    }

    @Test
    public void directionTest()
    {

    }

    @Test
    public void moveTest()
    {
        Position position = new Position(50, 50);
        entity.setWorldPosition(position);
        Assertions.assertEquals(entity.getWorldPosition(), position, "[Unexpected entity position]");
    }
}
