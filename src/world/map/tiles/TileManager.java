    package world.map.tiles;

    import main.controller.GameController;
    import utilities.FileManipulation;
    import utilities.sprite.SpriteSheet;

    import java.util.HashMap;
    import java.util.Map;


    public class TileManager
    {
        private static final Map<Integer, TileObject> tileObjects = new HashMap<>();
        public static TileObject getTileObject(int id) {return tileObjects.get(id);}


        public TileManager(GameController gc)
        {
            initializeTileObjects();
        }

        private void createTileObject(String spriteSheetPath, boolean isColliding, float traversalCost, String name, int id)
        {
            SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 64);
            TileObject tileObject = new TileObject(spriteSheet, isColliding, traversalCost, name, id);

            if (!tileObjects.containsKey(id))
            {
                tileObjects.put(id, tileObject);
            }
            else
            {
                throw new IllegalArgumentException("TileObject with ID " + id + " already exists in tileObjects map.");
            }
        }

        private void createTileObject(String spriteSheetPath, String edgedSpriteSheetPath, TileEdge tileEdge, boolean isColliding, float traversalCost, String name, int id)
        {
            SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 64);
            SpriteSheet edgedSpriteSheet = new SpriteSheet(FileManipulation.loadImage(edgedSpriteSheetPath), 64);
            TileObject tileObject = new TileObject(spriteSheet, edgedSpriteSheet, tileEdge, isColliding, traversalCost, name, id);
            if (!tileObjects.containsKey(id))
            {
                tileObjects.put(id, tileObject);
            }
            else
            {
                throw new IllegalArgumentException("TileObject with ID " + id + " already exists in tileObjects map.");
            }
        }

        private void initializeTileObjects()
        {
            createTileObject("resources/default/defaultTile",   false, 1, "Default Tile", TileID.DEFAULT_TILE.getId());
            createTileObject("resources/default/defaultTileCollision",  true, 0, "Default Tile Collision", TileID.DEFAULT_TILE_COLLISION.getId());
            createTileObject("resources/tiles/Dirt",  false, 1, "Dirt", TileID.DIRT.getId());
            createTileObject("resources/tiles/Grass",  false, 1, "Grass", TileID.GRASS.getId());
            createTileObject("resources/tiles/Stone", "resources/edges/Stone", TileEdge.STONE,  true, 0, "Stone", TileID.STONE.getId());
            createTileObject("resources/tiles/Rock", "resources/edges/Stone", TileEdge.STONE,  true, 0, "Rock",   TileID.ROCK.getId());
            createTileObject("resources/tiles/Sand",  false, 0.85f, "Sand", TileID.SAND.getId());
            createTileObject("resources/tiles/Water", "resources/edges/Water", TileEdge.WATER,false, 0.33f, "Water", TileID.WATER.getId());
            createTileObject("resources/tiles/CaveEntrance", false, 1, "Cave Entrance", TileID.CAVE_ENTRANCE.getId());
            createTileObject("resources/tiles/CaveExit",  false, 1, "Cave Exit", TileID.CAVE_EXIT.getId());
            createTileObject("resources/tiles/CaveFloor",  false, 1, "Cave Floor", TileID.CAVE_FLOOR.getId());
            createTileObject("resources/tiles/Gravel", false, 1, "Gravel", TileID.GRAVEL.getId());
            createTileObject("resources/tiles/BasaltFloor",  false, 1, "Basalt Floor", TileID.BASALT_FLOOR.getId());
            createTileObject("resources/tiles/DeepCaveEntrance",  false, 1, "Deep Cave Entrance", TileID.CAVE_DEEP_ENTRANCE.getId());
            createTileObject("resources/tiles/DeepCaveExit",  false, 1, "Deep Cave Exit", TileID.CAVE_DEEP_EXIT.getId());
        }
    }
