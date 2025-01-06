package world.map;

import main.Updatable;

public class MapUpdater implements Updatable
{
    MapController mapController;

    public MapUpdater(MapController mapController)
    {
        this.mapController = mapController;
    }

    @Override
    public void update()
    {

    }
}
