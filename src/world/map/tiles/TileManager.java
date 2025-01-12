    package world.map.tiles;

    import main.GameController;
    import utilities.FileManipulation;
    import utilities.sprite.Sprite;
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

        private void createTileObject(String spriteSheetPath, boolean isColliding, String name, int id)
        {
            SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 64);
            TileObject tileObject = new TileObject(spriteSheet, isColliding, name, id);

            if (!tileObjects.containsKey(id))
            {
                tileObjects.put(id, tileObject);
            }
            else
            {
                throw new IllegalArgumentException("TileObject with ID " + id + " already exists in tileObjects map.");
            }
        }

        private void createTileObject(String spriteSheetPath, String edgedSpriteSheetPath, boolean isColliding, String name, int id)
        {
            SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 64);
            SpriteSheet edgedSpriteSheet = new SpriteSheet(FileManipulation.loadImage(edgedSpriteSheetPath), 64);
            TileObject tileObject = new TileObject(spriteSheet, edgedSpriteSheet, isColliding, name, id);
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
            createTileObject("resources/default/defaultTile",   false, "Default Tile", TileID.DEFAULT_TILE.getId());
            createTileObject("resources/default/defaultTileCollision",  true, "Default Tile Collision", TileID.DEFAULT_TILE_COLLISION.getId());
            createTileObject("resources/tiles/Dirt",  false, "Dirt", TileID.DIRT.getId());
            createTileObject("resources/tiles/Grass",  false, "Grass", TileID.GRASS.getId());
            createTileObject("resources/tiles/Stone", "resources/edges/Stone",  true, "Stone", TileID.STONE.getId());
            createTileObject("resources/tiles/Rock", "resources/edges/Stone",  true, "Rock", TileID.ROCK.getId());
            createTileObject("resources/tiles/Sand",  false, "Sand", TileID.SAND.getId());
            createTileObject("resources/tiles/Water", "resources/edges/Water",  false, "Water", TileID.WATER.getId());
            createTileObject("resources/tiles/CaveEntrance", false, "Cave Entrance", TileID.CAVE_ENTRANCE.getId());
            createTileObject("resources/tiles/CaveExit",  false, "Cave Exit", TileID.CAVE_EXIT.getId());
            createTileObject("resources/tiles/CaveFloor",  false, "Cave Floor", TileID.CAVE_FLOOR.getId());
            createTileObject("resources/tiles/Gravel", false, "Gravel", TileID.GRAVEL.getId());
            createTileObject("resources/tiles/BasaltFloor",  false, "Basalt Floor", TileID.BASALT_FLOOR.getId());
            createTileObject("resources/tiles/DeepCaveEntrance",  false, "Deep Cave Entrance", TileID.CAVE_DEEP_ENTRANCE.getId());
            createTileObject("resources/tiles/DeepCaveExit",  false, "Deep Cave Exit", TileID.CAVE_DEEP_EXIT.getId());
        }
    }
