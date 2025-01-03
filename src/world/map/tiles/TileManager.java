    package world.map.tiles;

    import main.GameController;
    import utilities.FileManipulation;
    import utilities.Sprite;
    import utilities.SpriteSheet;

    import java.util.HashMap;
    import java.util.Map;


    public class TileManager
    {
        private static final Map<Integer, TileObject> tileObjects = new HashMap<>();
        public static final Tile defaultTileObject = new Tile();

        public enum TileID
        {
            DEFAULT_TILE(-2),
            DEFAULT_TILE_COLLISION(-1),
            DIRT(0),
            GRASS(1),
            STONE(2),
            ROCK(3),
            SAND(4),
            WATER(5),
            CAVE_ENTRANCE(6);

            private final int id;

            TileID(int id) {this.id = id;}

            public int getId() {return id;}
        }

        public static TileObject getTileObject(int id) {return tileObjects.get(id);}


        public TileManager(GameController gc)
        {
            initializeTileObjects();
        }

        public static class TileObject
        {
            private Sprite[][] sprites;
            private SpriteSheet spriteSheet;
            private boolean isColliding;
            private String name;
            private int id;

            public SpriteSheet getSpriteSheet() {return spriteSheet;}
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

        public void initializeTileObjects()
        {
            createTileObject("resources/default/defaultTile", false, "Default Tile", TileID.DEFAULT_TILE.getId());
            createTileObject("resources/default/defaultTileCollision", true, "Default Tile Collision", TileID.DEFAULT_TILE_COLLISION.getId());
            createTileObject("resources/tiles/Dirt", false, "Dirt", TileID.DIRT.getId());
            createTileObject("resources/tiles/Grass", false, "Grass", TileID.GRASS.getId());
            createTileObject("resources/tiles/Stone", true, "Stone", TileID.STONE.getId());
            createTileObject("resources/tiles/Rock", true, "Rock", TileID.ROCK.getId());
            createTileObject("resources/tiles/Sand", false, "Sand", TileID.SAND.getId());
            createTileObject("resources/tiles/Water", false, "Water", TileID.WATER.getId());
            createTileObject("resources/tiles/CaveEntrance", false, "Cave Entrance", TileID.CAVE_ENTRANCE.getId());
        }
    }
