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
        Node startNode = new Node(MapController.getCurrentMap().getTile(startPos));
        Node endNode = new Node(MapController.getCurrentMap().getTile(endPos));

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getfCost)); // unvisited nodes, priority based on fCost of node
        Set<Position> closedList = new HashSet<>(); // visited nodes
        Map<Position, Node> nodes = new HashMap<>();
        openList.add(startNode);

        Position[] directions =
                {
                        new Position(-1, 0),    // up
                        new Position(1, 0),     // down
                        new Position(0, -1),    // left
                        new Position(0, 1),     // right
                        new Position(-1, -1),   // up-left
                        new Position(-1, 1),    // up-right
                        new Position(1, -1),    // down-left
                        new Position(1, 1)      // down-right
                };

        nodes.put(startPos, startNode);
        while (!openList.isEmpty())
        {
            Node currentNode = openList.poll();
            closedList.add(currentNode.getPosition());

            // If the target is reached, reconstruct the path
            if (currentNode.getPosition().equals(endNode.getPosition()))
            {
                LinkedList<Position> path = new LinkedList<>();
                Node current = currentNode;
                Node previous = current;
                while (current != null)
                {

                    for (Position direction : directions)
                    {
                        int maxX = Math.max(previous.getPosition().x, current.getPosition().x);
                        int minX = Math.min(previous.getPosition().x, current.getPosition().x);
                        int maxY = Math.max(previous.getPosition().y, current.getPosition().y);
                        int minY = Math.min(previous.getPosition().y, current.getPosition().y);


                        System.out.println(":" + (maxX-minX));
                        System.out.println(":" + (maxY-minY));
                        if (((maxX-minX) == (maxY-minY) || (maxX - minX) == (maxX) || (maxX - minX) == (minX)
                                || (maxY - minY) == (maxY) || (maxY - minY) == (minY)))
                        {
                            path.addFirst(current.getPosition());
                            break;
                        }
                    }
                    previous = current;
                    current = current.getParent();
                }
                return path.toArray(new Position[0]);
            }

            // Process neighbors
            List<Node> neighbors = Node.getNeighbors(currentNode);
            for (Node neighbor : neighbors)
            {
                if (closedList.contains(neighbor.getPosition()) || !neighbor.isPassable())
                {
                    continue;
                }
                Tile neighborTile = MapController.getCurrentMap().getTile(neighbor.getPosition());
                neighbor.calculateCosts(startNode, endNode, neighborTile);

                Node existingNeighbor = nodes.get(neighbor.getPosition());

                if (existingNeighbor == null || neighbor.getfCost() < existingNeighbor.getfCost())
                {
                    neighbor.setParent(currentNode);

                    if (existingNeighbor == null)
                    {
                        openList.add(neighbor);
                        nodes.put(neighbor.getPosition(), neighbor);
                    }
                    else
                    {
                        openList.remove(existingNeighbor); // Remove outdated node
                        openList.add(neighbor);            // Add updated node
                    }
                }
            }
        }
        return null; // If no path is found, return null
    }
}
