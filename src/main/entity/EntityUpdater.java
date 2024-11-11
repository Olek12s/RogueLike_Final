package main.entity;

import main.Updatable;

public class EntityUpdater implements Updatable
{
    private Entity entity;

    public EntityUpdater(Entity entity)
    {
        this.entity = entity;
    }

    @Override
    public void update()
    {

    }
}
