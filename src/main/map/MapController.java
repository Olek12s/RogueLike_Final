package main.map;

import main.GameController;

public class MapController
{
    public GameController gc;
    private MapRenderer renderer = new MapRenderer(this);
    private MapUpdater updater = new MapUpdater(this);
    private Map currentMap;

    public Map getCurrentMap() {return currentMap;}

    public MapController(GameController gc)
    {
        this.gc = gc;
        this.currentMap = new Map(64, 64, "resources/maps/world0-0.txt");   // temporary

        gc.updatables.add(updater);
        gc.drawables.add(renderer);
    }
}
