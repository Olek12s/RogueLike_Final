package utilities;

import main.GameController;
import main.entity.Entity;
import world.map.Chunk;
import world.map.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Collisions
{
    static GameController gc;

    public Collisions(GameController gc)
    {
        this.gc = gc;
    }

    public static boolean isColliding(Entity entity)
    {
        Rectangle hitbox = entity.getHitbox().getHitboxRect();
        Rectangle preditctedHitboxRect = new Rectangle(hitbox);
        boolean isColliding = false;
        preditctedHitboxRect = predictFutureHitboxRectPosition(entity);

        try
        {
            Tile tileUpLeft = gc.mapController.getCurrentMap().getTile(preditctedHitboxRect.x, preditctedHitboxRect.y);
            Tile tileDownLeft = gc.mapController.getCurrentMap().getTile(preditctedHitboxRect.x, preditctedHitboxRect.y + preditctedHitboxRect.height);
            Tile tileUpRight = gc.mapController.getCurrentMap().getTile(preditctedHitboxRect.x + preditctedHitboxRect.width, preditctedHitboxRect.y);
            Tile tileDownRight = gc.mapController.getCurrentMap().getTile(preditctedHitboxRect.x + preditctedHitboxRect.width, preditctedHitboxRect.y + preditctedHitboxRect.height);
            if (tileUpLeft.isColliding() == true || tileDownLeft.isColliding() == true || tileUpRight.isColliding() == true || tileDownRight.isColliding() == true)
            {
                isColliding = true;
            }
        }
        catch (IndexOutOfBoundsException ex)    // out of map
        {
            isColliding = true;
        }
        return isColliding;
    }

    /*
    public static boolean isCollidingWithOtherHitbox(Entity entity)
    {
        Hitbox sourceHitbox = entity.getHitbox();
        Rectangle preditctedHitboxRect = predictFutureHitboxRectPosition(entity);
        Position sourceHitboxCenter = sourceHitbox.getCenterPosition();
        boolean isColliding = false;

        //get all chunks, where sourceHitbox vertices are belonging - done
        //get all chunks, where sourceHitbox is belonging
        //check if sourceHitbox is intersecting with all the hitboxes from above chunks
        //if sourceHitbox is intersecting with at least 1 hitbox, return true (eventually array of hitboxes/collidables it is colliding)
        //else return false

        Position[] verticesPositions = new Position[4];
        verticesPositions[0] = new Position(preditctedHitboxRect.x, preditctedHitboxRect.y);
        verticesPositions[1] = new Position(preditctedHitboxRect.x, preditctedHitboxRect.y + sourceHitbox.getHeight());
        verticesPositions[2] = new Position(preditctedHitboxRect.x + sourceHitbox.getWidth(), preditctedHitboxRect.y);
        verticesPositions[3] = new Position(preditctedHitboxRect.x + sourceHitbox.getWidth(), preditctedHitboxRect.y + sourceHitbox.getHeight());


        Set<Chunk> verticesChunks = new HashSet<>();
        for (int i = 0; i < verticesPositions.length; i++)
        {
            if (gc.mapController.getCurrentMap().getChunk(verticesPositions[i]) != null)
            {
                verticesChunks.add(gc.mapController.getCurrentMap().getChunk(verticesPositions[i]));
            }
        }
        if (verticesChunks.size() == 1) // if sourceHitbox is fitting in only one chunk - check all hitboxes from this chunk
        {
           Chunk chunk = verticesChunks.stream().findFirst().orElse(null);

               for (Entity cEntity : chunk.getEntities())
               {
                   if (cEntity.getHitbox() == sourceHitbox) continue;
                   if(cEntity.getHitbox().getHitboxRect().intersects(preditctedHitboxRect))
                   {
                       return true;
                   }
               }
        }

       else // else check verticeChunk and its two of four non-diagonal neighbors, which are closest to the center of sourceHitBox
       {
           //ArrayList<Chunk> chunksPotentiallyBelongingToHitbox = new ArrayList<>();
            for (Chunk chunk : verticesChunks)
            {
                ArrayList<Chunk> chunkNeighbors = gc.mapController.getCurrentMap().getChunkNeighborsNotDiagonals(chunk);

                for (Chunk neighborChunk : chunkNeighbors)
                {
                    for (Entity cEntity : neighborChunk.getEntities())
                    {
                        if (cEntity.getHitbox() == sourceHitbox) continue;
                        if(cEntity.getHitbox().getHitboxRect().intersects(preditctedHitboxRect))
                        {
                            return true;
                        }
                    }
                }
                //potentialHitboxChunks.add()
            }
       }
        return isColliding;
    }
     */

    public static boolean isCollidingWithOtherHitbox(Entity entity)
    {
        Hitbox sourceHitbox = entity.getHitbox();
        Rectangle predictedHitboxRect = predictFutureHitboxRectPosition(entity);
        Set<Chunk> affectedChunks = new HashSet<>();

        Position[] verticesPositions = new Position[4];
        verticesPositions[0] = new Position(predictedHitboxRect.x, predictedHitboxRect.y);
        verticesPositions[1] = new Position(predictedHitboxRect.x, predictedHitboxRect.y + predictedHitboxRect.height);
        verticesPositions[2] = new Position(predictedHitboxRect.x + predictedHitboxRect.width, predictedHitboxRect.y);
        verticesPositions[3] = new Position(predictedHitboxRect.x + predictedHitboxRect.width, predictedHitboxRect.y + predictedHitboxRect.height);

        for (Position vertex : verticesPositions)
        {
            Chunk chunk = gc.mapController.getCurrentMap().getChunk(vertex);
            if (chunk != null)
            {
                affectedChunks.add(chunk);
                ArrayList<Chunk> neighbors = gc.mapController.getCurrentMap().getChunkNeighborsNotDiagonals(chunk);
                affectedChunks.addAll(neighbors);
            }
        }

        for (Chunk chunk : affectedChunks)
        {
            for (Entity otherEntity : chunk.getEntities())
            {
                if (otherEntity == entity) continue; // ignore source
                if (otherEntity.getHitbox().getHitboxRect().intersects(predictedHitboxRect))
                {
                    return true;
                }
            }
        }
        return false;
    }


    private static Rectangle predictFutureHitboxRectPosition(Entity entity)
    {
        Rectangle hitbox = entity.getHitbox().getHitboxRect();
        Rectangle preditctedHitbox = new Rectangle(hitbox);

        switch (entity.getDirection())   // finding out future hitbox position
        {
            case DOWN:
                preditctedHitbox.y += entity.getMovementSpeed();
                break;
            case UP:
                preditctedHitbox.y -= entity.getMovementSpeed();
                break;
            case LEFT:
                preditctedHitbox.x -= entity.getMovementSpeed();
                break;
            case RIGHT:
                preditctedHitbox.x += entity.getMovementSpeed();
                break;
            case UP_LEFT:
                preditctedHitbox.x -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case UP_RIGHT:
                preditctedHitbox.x += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case DOWN_LEFT:
                preditctedHitbox.x -= Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
            case DOWN_RIGHT:
                preditctedHitbox.x += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                preditctedHitbox.y += Math.max((int) (entity.getMovementSpeed() / Math.sqrt(2)), 1);
                break;
        }
        return preditctedHitbox;
    }

}
