package world.generation;

import main.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.bitingSlime.*;
import utilities.Position;
import world.map.Map;
import world.map.MapController;
import world.map.MapLevels;
import world.map.tiles.Tile;

import java.util.Random;

public class EntityGenerator
{
    private long seed;
    public GameController gc;
    static Random random;

    public EntityGenerator(GameController gc)
    {
        this.gc = gc;
        this.seed = TerrainGenerator.getSeed();
        random = new Random(seed);
        spawnEntitiesTest();

    }

    private void spawnEntitiesTest()
    {
        int counter = 0;
        for (int i = 0; i < 2000; i++)
        {
            spawnEntityAtRandomLocation(MapLevels.SURFACE,  EntityID.MiniBitingSlime);
            System.out.println(counter++);
        }
    }

    public void spawnEntityAtRandomLocation(MapLevels mapLevel, EntityID entityID)
    {
        Map map = MapController.getMapByLevel(mapLevel);
        int width = map.getMapWidthInPixels();
        int height = map.getMapHeightInPixels();

        int minX = -(width/2) + 2500;
        int maxX = (width / 2) - 2500;
        int randomX = random.nextInt(maxX - minX + 1) + minX;

        int minY = -(height/2) + 500;
        int maxY = (height / 2) - 500;
        int randomY = random.nextInt(maxY - minY + 1) + minY;

        Position randomPosition = new Position(randomX, randomY);
        Entity entity = createEntityObject(entityID, randomPosition);
        map.getChunk(randomPosition).addEntity(entity);
    }

    private Entity createEntityObject(EntityID entityID, Position position)
    {
        return switch (entityID)
        {
            case MiniBitingSlime -> new MiniBitingSlime(gc, position);
            case BitingSlime -> new BitingSlime(gc, position);
            case Spider -> new Spider(gc, position);
            case VenomousSpider -> new VenomousSpider(gc, position);
            case Zombie -> new Zombie(gc, position);
            default -> throw new IllegalArgumentException("Unexpected EntityID: " + entityID);
        };
    }
}
