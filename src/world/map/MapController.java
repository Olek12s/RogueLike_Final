package world.map;

import main.GameController;
import main.entity.player.Player;
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
    static int mapWidth;
    static int mapHeight;

    public static SurfaceGenerator surfaceGenerator;

    public Map getCurrentMap()
    {
        return currentMap;
    }

    public MapController(GameController gc, int width, int height)
    {
        this.gc = gc;
        mapWidth = width;
        mapHeight = height;
        SurfaceGenerator.createSurfaceMap(width, height);
        createMap(0, width, height);
        this.currentMap = maps.get(0);
        maps.put(0, currentMap);    // surface level

        gc.updatables.add(updater);
        gc.drawables.add(renderer);
    }

    // Changes maps between levels -2 -1 0 1 [...]
    private void changeCurrentMap(int level)
    {
        this.currentMap = maps.get(level);
    }

    public void changeMapForPlayer(Player player, int level)
    {
        if (maps.get(level) == null)    // if map did not exist, generate it and put in the collection
        {
            createMap(level, mapWidth, mapHeight);
        }
        changeCurrentMap(level);
    }

    public static void createMap(int level, int width, int height)
    {
        MapLevels mapLevel;
        mapLevel = MapLevels.fromId(level);

        switch (mapLevel)
        {
            case CAVE_NEGATIVE_TWO:
                maps.put(-2, new Map(width/Chunk.getChunkSize(), height/Chunk.getChunkSize(), "resources/maps/CaveNegTwo.txt", MapLevels.CAVE_NEGATIVE_TWO));
                break;
            case CAVE_NEGATIVE_ONE:
                maps.put(-1, new Map(width/Chunk.getChunkSize(), height/Chunk.getChunkSize(), "resources/maps/CaveNegOne.txt",  MapLevels.CAVE_NEGATIVE_ONE));
                break;
            case SURFACE:
                maps.put(0, new Map(width/Chunk.getChunkSize(), height/Chunk.getChunkSize(), "resources/maps/Surface.txt",  MapLevels.SURFACE));
                break;
        }
    }
}
