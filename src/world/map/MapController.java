package world.map;

import main.controller.GameController;
import main.entity.player.Player;
import world.generation.SurfaceGenerator;

import java.util.HashMap;

public class MapController
{
    public GameController gc;
    private MapRenderer renderer;
    private MapUpdater updater;
    private static Map currentMap;
    private static HashMap<Integer, Map> maps = new HashMap<>();
    static int mapWidth;
    static int mapHeight;

    public static Map getCurrentMap()
    {
        return currentMap;
    }
    public static Map getMapByLevel(MapLevels level)
    {
        return  maps.get(level.getValue());
    }

    public MapController(GameController gc, int width, int height)
    {
        this.gc = gc;
        mapWidth = width;
        mapHeight = height;SurfaceGenerator.createSurfaceMap(width, height);
        createMap(0, width, height);
        this.currentMap = maps.get(0);
        maps.put(0, currentMap);    // surface level

        renderer = new MapRenderer(this);
        updater = new MapUpdater(this);
        gc.updatables.add(updater);
        gc.drawables.add(renderer);
    }

    // Changes maps between levels -2 -1 0 1 [...]
    private void changeCurrentMap(int level)
    {
        this.currentMap = maps.get(level);
    }

    public void changeMapForPlayer(Player player, MapLevels level)
    {
        if (maps.get(level.getValue()) == null)    // if map did not exist, generate it and put in the collection
        {
            createMap(level.getValue(), mapWidth, mapHeight);
        }
        player.setLevel(level);
        changeCurrentMap(level.getValue());
    }

    public static void createMap(int level, int width, int height)
    {
        MapLevels mapLevel;
        mapLevel = MapLevels.fromId(level);

        switch (mapLevel)
        {
            case CAVE_NEGATIVE_TWO:
                maps.put(MapLevels.CAVE_NEGATIVE_TWO.getValue(), new Map(width/Chunk.getChunkSize(), height/Chunk.getChunkSize(), "resources/maps/CaveNegTwo.txt", MapLevels.CAVE_NEGATIVE_TWO));
                break;
            case CAVE_NEGATIVE_ONE:
                maps.put(MapLevels.CAVE_NEGATIVE_ONE.getValue(), new Map(width/Chunk.getChunkSize(), height/Chunk.getChunkSize(), "resources/maps/CaveNegOne.txt",  MapLevels.CAVE_NEGATIVE_ONE));
                break;
            case SURFACE:
                maps.put(MapLevels.SURFACE.getValue(), new Map(width/Chunk.getChunkSize(), height/Chunk.getChunkSize(), "resources/maps/Surface.txt",  MapLevels.SURFACE));
                break;
        }
    }
}
