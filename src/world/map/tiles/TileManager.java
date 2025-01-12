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

        public static class TileObject
        {
            private Sprite[][] sprites;
            private Sprite[] edgeSprites;
            private SpriteSheet spriteSheet;
            private boolean isColliding;
            private String name;
            private int id;

            public SpriteSheet getSpriteSheet() {return spriteSheet;}
            public Sprite[] getEdgeSprites() {return edgeSprites;}
            public boolean isColliding() {return isColliding;}
            public int getId() {return id;}
            public String getName() {return name;}
            public Sprite[][] getSprites() {return sprites;}

            public Sprite getRandomVariation(int animationTick)
            {
                int variation = (int) (Math.random() * (spriteSheet.variations));

                return sprites[variation][animationTick];
            }

            public TileObject(SpriteSheet spriteSheet, boolean isColliding, String name, int id)
            {
                this.sprites = new Sprite[spriteSheet.variations][spriteSheet.ticks];
                this.spriteSheet = spriteSheet;
                this.isColliding = isColliding;
                this.name = name;
                this.id = id;
                this.edgeSprites = getTileEdgeSprites(id);

                for (int variation = 0; variation < spriteSheet.variations; variation++)
                {
                    for (int tick = 0; tick < spriteSheet.ticks; tick++)
                    {
                        sprites[variation][tick] = spriteSheet.extractSprite(spriteSheet, tick, variation);
                    }
                }
            }
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

        private void initializeTileObjects()
        {
            createTileObject("resources/default/defaultTile",   false, "Default Tile", TileID.DEFAULT_TILE.getId());
            createTileObject("resources/default/defaultTileCollision",  true, "Default Tile Collision", TileID.DEFAULT_TILE_COLLISION.getId());
            createTileObject("resources/tiles/Dirt",  false, "Dirt", TileID.DIRT.getId());
            createTileObject("resources/tiles/Grass",  false, "Grass", TileID.GRASS.getId());
            createTileObject("resources/tiles/Stone",  true, "Stone", TileID.STONE.getId());
            createTileObject("resources/tiles/Rock",  true, "Rock", TileID.ROCK.getId());
            createTileObject("resources/tiles/Sand",  false, "Sand", TileID.SAND.getId());
            createTileObject("resources/tiles/Water",  false, "Water", TileID.WATER.getId());
            createTileObject("resources/tiles/CaveEntrance", false, "Cave Entrance", TileID.CAVE_ENTRANCE.getId());
            createTileObject("resources/tiles/CaveExit",  false, "Cave Exit", TileID.CAVE_EXIT.getId());
            createTileObject("resources/tiles/CaveFloor",  false, "Cave Floor", TileID.CAVE_FLOOR.getId());
            createTileObject("resources/tiles/Gravel", false, "Gravel", TileID.GRAVEL.getId());
            createTileObject("resources/tiles/BasaltFloor",  false, "Basalt Floor", TileID.BASALT_FLOOR.getId());
            createTileObject("resources/tiles/DeepCaveEntrance",  false, "Deep Cave Entrance", TileID.CAVE_DEEP_ENTRANCE.getId());
            createTileObject("resources/tiles/DeepCaveExit",  false, "Deep Cave Exit", TileID.CAVE_DEEP_EXIT.getId());
        }

        private static Sprite[] getTileEdgeSprites(int tileID)
        {
            TileID tile = TileID.fromId(tileID);
            String spriteSheetPath;

            switch (tile)
            {
                case WATER: spriteSheetPath = "resources/edges/Water"; break;
                case STONE: spriteSheetPath = "resources/edges/Stone"; break;
                case ROCK: spriteSheetPath = "resources/edges/Stone"; break;
                default: return null;
            }

            SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 64);
            Sprite[] sprites = new Sprite[spriteSheet.variations];

            for (int i = 0; i < spriteSheet.variations; i++)
            {
                sprites[i] = spriteSheet.extractSprite(spriteSheet, 0, i);
            }
            return sprites;
        }
    }
