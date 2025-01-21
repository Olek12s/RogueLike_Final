package utilities.pathfinding.astar;

import main.entity.Entity;
import utilities.Position;
import world.map.MapController;
import world.map.tiles.Tile;

public class AStar
{
    /**
     * returns path from entitySource to entityTarget
     *
     * @param entitySource
     * @param entityTarget
     * @return an array of n positions, where: P0 is starting points, Pn-1 is middle points of entityTarget's hitbox.
     * New points between P0 and Pn-1 is created, whenever there's need to make a turn to in order to
     * follow the best heuristics or avoid an obstacle such as other hitbox / collidable tile
     */
    public static Position[] getPathToEntity(Entity entitySource, Entity entityTarget)
    {
        Position startPos = entitySource.getWorldPosition();
        Position endPos = entityTarget.getWorldPosition();
        Node startNode = new Node(startPos, MapController.getCurrentMap().getTile(startPos));
        Node endNode = new Node(startPos, MapController.getCurrentMap().getTile(startPos));





        return null;
    }
}
