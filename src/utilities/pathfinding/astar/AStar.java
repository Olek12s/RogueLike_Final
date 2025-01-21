package utilities.pathfinding.astar;

import main.entity.Entity;
import utilities.Position;
import world.map.MapController;
import world.map.tiles.Tile;

import java.util.*;

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
        Node endNode = new Node(startPos, MapController.getCurrentMap().getTile(endPos));

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getfCost)); // priority based on fCost of node
        openList.add(startNode);

        List<Node> closedList = new ArrayList<>();

        while (!openList.isEmpty())
        {
            Node currentNode = openList.poll();

            // if target was reach - reconstruct path
            if (currentNode.getPosition().equals(endNode.getPosition()))
            {
                LinkedList<Position> path = new LinkedList<>();
                Node current = currentNode;
                while (current != null)
                {
                    path.addFirst(current.getPosition());
                    current = current.getParent();
                }
                return path.toArray(new Position[0]);
            }
            closedList.add(currentNode);

            List<Node> neighbors = Node.getNeighbors(currentNode);
            for (Node neighbor : neighbors)
            {
                if (closedList.contains(neighbor)) continue;

                neighbor.calculateCosts(startNode, endNode, MapController.getCurrentMap().getTile(neighbor.getPosition()));
                if (!openList.contains(neighbor))
                {
                    neighbor.setParent(currentNode);
                    openList.add(neighbor);
                }
            }
        }
        return null; // If no path is found, return null
    }
}
