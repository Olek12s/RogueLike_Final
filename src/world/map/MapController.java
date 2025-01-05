package world.map;

import main.GameController;
import world.generation.SurfaceGenerator;
import world.generation.TerrainGenerator;

import java.util.HashMap;
import java.util.Set;

public class MapController
{
    public GameController gc;
    private MapRenderer renderer = new MapRenderer(this);
    private MapUpdater updater = new MapUpdater(this);
    private Map currentMap;
    private static HashMap<Integer, Map> maps = new HashMap<>();
    int mapWidth;
    int mapHeight;

    public static SurfaceGenerator surfaceGenerator;

    public Map getCurrentMap() {return currentMap;}

    public MapController(GameController gc, int width, int height)
    {
        this.gc = gc;
        this.mapWidth = width;
        this.mapHeight = height;
        SurfaceGenerator.createSurfaceMap(width, height);
        this.currentMap = new Map(mapWidth/Chunk.getChunkSize(), mapHeight/Chunk.getChunkSize(), "resources/maps/Surface.txt");
        maps.put(0, currentMap);    // surface level

        gc.updatables.add(updater);
        gc.drawables.add(renderer);
    }

    // Changes maps between levels -2 -1 0 1 [...]
    public void changeMap(int level)
    {
        this.currentMap = maps.get(level);
    }

    public void addMap(int level, Map map)
    {
        maps.put(level, map);
    }


}
