    package main.map;

    import main.GameController;
    import utilities.FileManipulation;
    import utilities.SpriteSheet;

    import java.util.HashMap;
    import java.util.Map;


    public class TileManager
    {
        private static int TM_ID = 0;
        private static final Map<Integer, TileObject> tileObjects = new HashMap<>();
        public static final Tile defaultTileObject = new Tile();

        public static TileObject getTileObject(int id) {return tileObjects.get(id);}


        public TileManager(GameController gc)
        {
            //this.tileObjects = new HashMap<>();
            initializeTileObjects();
        }

        public static class TileObject
        {
            private SpriteSheet spriteSheet;
            private boolean isColliding;
            private int id;

            public SpriteSheet getSpriteSheet() {return spriteSheet;}
            public boolean isColliding() {return isColliding;}
            public int getId() {return id;}

            public TileObject(SpriteSheet spriteSheet, boolean isColliding, int id)
            {
                this.spriteSheet = spriteSheet;
                this.isColliding = isColliding;
                this.id = id;
            }
        }

        private void createTileObject(String spriteSheetPath, boolean isColliding, int id)
        {
            SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage(spriteSheetPath), 64);
            TileObject tileObject = new TileObject(spriteSheet, isColliding, id);

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
            createTileObject("resources/default/defaultTile", false, TM_ID++);
            createTileObject("resources/default/defaultTileCollision", true, TM_ID++);
        }
    }
