package main.entity.player;

import main.Direction;
import main.controller.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.item.Item;
import utilities.*;
import utilities.sprite.SpriteSheet;
import world.map.Chunk;
import world.map.tiles.Tile;

import java.util.List;

public class Player extends Entity
{
    public boolean isCollidingWithEnterance = false;

    public Player(GameController gc)
    {
        super(gc, EntityID.Player.ID, new Position(0,0)); // player's entityID is 0!
        int mapSizeInPixels = gc.mapController.getCurrentMap().getMapWidthInPixels();
        int halfMapSizeInTiles = (mapSizeInPixels/Tile.tileSize)/2;
       // Position spawnPosition = randomPlayerStartingPosition(mapSizeInPixels, mapSizeInPixels, halfMapSizeInTiles/4, halfMapSizeInTiles/2);
        Position spawnPosition = new Position(64*9, 0);
        setWorldPosition(gc.mapController.getCurrentMap().seekForNearestNonCollidableSpawnPosition(spawnPosition, hitbox));
        setupStatistics();
        entityUpdater.initUpdate();
    }



    @Override
    public PlayerRenderer setRenderer()
    {
        SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/entity/bitingSlimeGreen22Player"), 22);
        return new PlayerRenderer(this, spriteSheet);
    }

    @Override
    public PlayerUpdater setUpdater()
    {
        return new PlayerUpdater(this);
    }


    @Override
    public void setupStatistics()
    {
        statistics.setMaxHitPoints(100);
        statistics.setBaseArmour(0);
        statistics.setStrength(3);  //
        statistics.setRegeneration(1);
        statistics.setHitPoints(statistics.getMaxHitPoints());
        statistics.setMaxMovementSpeed(12);
        statistics.setNextLevelExp(5);
    }

    @Override
    public void setDetectionRadius() {
        setDetectionDiameter(0);
    }

    @Override
    public void setLoseInterestRadius() {
        setLoseInterestDiameter(0);
    }

    @Override
    public void setDefaultSprite()
    {
        //currentSprite = renderer.getSpriteSheet().extractFirst();
        currentSprite = PlayerRenderer.getSpriteSheetByID(entityID).extractFirst();
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, (int)(currentSprite.resolutionX), (int)(currentSprite.resolutionY));
    }

    @Override
    public void setDirection()
    {
        direction = Direction.DOWN;
    }

    @Override
    public void setWorldPosition(Position worldPosition)
    {
        this.worldPosition = worldPosition;
    }


    /**
     *Generates a random starting position for the player within the specified map boundaries.
     *The position is restricted to avoid spawning too close to the center of the map or the map edges.
     *
     * @param mapWidthInPixels          width of the map in pixels.
     * @param mapHeightInPixels         height of the map in pixels.
     * @param tilesAwayFromMapMiddle    The number of tiles to exclude around the center of the map.
     * @param tilesAwayFromMapEdge      The number of tiles to exclude near the edges of the map.
     * @return  A random {@link Position} object representing the player's starting coordinates
     */
    private Position randomPlayerStartingPosition(int mapWidthInPixels, int mapHeightInPixels, int tilesAwayFromMapMiddle, int tilesAwayFromMapEdge)
    {
        int tileSize = Tile.tileSize;


        int centerX = mapWidthInPixels / 2;
        int centerY = mapHeightInPixels / 2;


        int exclusionRadius = tilesAwayFromMapMiddle * tileSize;

        int exclusionMinX = centerX - exclusionRadius;
        int exclusionMaxX = centerX + exclusionRadius;
        int exclusionMinY = centerY - exclusionRadius;
        int exclusionMaxY = centerY + exclusionRadius;


        int edgeLimitMinX = tilesAwayFromMapEdge * tileSize;
        int edgeLimitMaxX = mapWidthInPixels - tilesAwayFromMapEdge * tileSize;
        int edgeLimitMinY = tilesAwayFromMapEdge * tileSize;
        int edgeLimitMaxY = mapHeightInPixels - tilesAwayFromMapEdge * tileSize;

        Position position;
        int randomX;
        int randomY;

        do {

            randomX = (int) (Math.random() * (edgeLimitMaxX - edgeLimitMinX)) + edgeLimitMinX;
            randomY = (int) (Math.random() * (edgeLimitMaxY - edgeLimitMinY)) + edgeLimitMinY;

            position = new Position(randomX - mapWidthInPixels / 2, randomY - mapHeightInPixels / 2);


        } while (randomX >= exclusionMinX && randomX <= exclusionMaxX &&
                randomY >= exclusionMinY && randomY <= exclusionMaxY);

        return position;
    }

    public void pickUpItem()
    {
        Chunk currentChunk = gc.mapController.getCurrentMap().getChunk(this.getWorldPosition());
        if (currentChunk == null) return;

        List<Item> itemsInChunk = currentChunk.getItems();
        for (Item item : itemsInChunk)  // checking if player's hitbox intersects item's hitbox, then item can be added to inventory
        {
            if (this.getHitbox().getHitboxRect().intersects(item.getHitbox().getHitboxRect()))
            {
                if (inventory.addItem(item))
                {
                    currentChunk.removeItem(item);
                    item.setOnGround(false);
                    item.setLevel(null);
                }
                return;
            }
        }
    }
}
