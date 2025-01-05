package main.entity.player;

import main.Direction;
import main.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.BitingSlimeWeapon;
import main.item.ZombieWeapon;
import utilities.*;
import world.map.tiles.Tile;

public class Player extends Entity
{
    public Player(GameController gc)
    {
        super(gc, EntityID.Player.ID, new Position(0,0)); // player's entityID is 0!
        int mapSizeInPixels = gc.mapController.getCurrentMap().getMapWidthInPixels();
        int halfMapSizeInTiles = (mapSizeInPixels/Tile.tileSize)/2;
        setWorldPosition(randomPlayerStartingPosition(mapSizeInPixels, mapSizeInPixels, halfMapSizeInTiles/4, halfMapSizeInTiles/2));
        //setWorldPosition(gc.mapController.getCurrentMap().seekForNearestNonCollidableSpawnPosition(new Position(0, -2000), hitbox));
        setupStatistics();

        entityUpdater.initUpdate();
    }



    @Override
    public PlayerRenderer setRenderer()
    {
        SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22);
        return new PlayerRenderer(this, spriteSheet);
    }

    @Override
    public PlayerUpdater setUpdater()
    {
        return new PlayerUpdater(this);
    }

    @Override
    public void attack(Entity target) {

    }

    @Override
    public void setupStatistics()
    {
        statistics.maxHitPoints = 100;
        statistics.setArmour(0);
        statistics.setRegeneration(1);
        statistics.hitPoints = statistics.maxHitPoints;
        statistics.setMovementSpeed(150);
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

        // Oblicz środek mapy w pikselach
        int centerX = mapWidthInPixels / 2;
        int centerY = mapHeightInPixels / 2;

        // Oblicz minimalne i maksymalne współrzędne w obszarze wykluczenia
        int exclusionRadius = tilesAwayFromMapMiddle * tileSize;

        int exclusionMinX = centerX - exclusionRadius;
        int exclusionMaxX = centerX + exclusionRadius;
        int exclusionMinY = centerY - exclusionRadius;
        int exclusionMaxY = centerY + exclusionRadius;

        // Oblicz minimalne i maksymalne współrzędne dla krawędzi mapy
        int edgeLimitMinX = tilesAwayFromMapEdge * tileSize;
        int edgeLimitMaxX = mapWidthInPixels - tilesAwayFromMapEdge * tileSize;
        int edgeLimitMinY = tilesAwayFromMapEdge * tileSize;
        int edgeLimitMaxY = mapHeightInPixels - tilesAwayFromMapEdge * tileSize;

        Position position;
        int randomX;
        int randomY;

        do {
            // Losuj współrzędne w obszarze ograniczonym przez krawędzie mapy
            randomX = (int) (Math.random() * (edgeLimitMaxX - edgeLimitMinX)) + edgeLimitMinX;
            randomY = (int) (Math.random() * (edgeLimitMaxY - edgeLimitMinY)) + edgeLimitMinY;

            position = new Position(randomX - mapWidthInPixels / 2, randomY - mapHeightInPixels / 2);

            // Sprawdzaj, czy wylosowana pozycja znajduje się w obszarze wykluczenia środka
        } while (randomX >= exclusionMinX && randomX <= exclusionMaxX &&
                randomY >= exclusionMinY && randomY <= exclusionMaxY);

        return position;
    }
}
