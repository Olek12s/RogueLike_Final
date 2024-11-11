package main.entity;

import main.Direction;
import main.Updatable;

public class EntityUpdater implements Updatable
{
    private Entity entity;

    public Entity getEntity() {return entity;}

    public EntityUpdater(Entity entity)
    {
        this.entity = entity;
        entity.gc.updatables.add(this);
    }

    @Override
    public void update()
    {

    }
}
