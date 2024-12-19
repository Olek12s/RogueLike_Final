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
        // to do: chunk loading (which chunk should be loaded and which not, map swithcing etc...
    }
}
