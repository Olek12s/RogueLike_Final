package utilities.pathfinding.astar;

import main.entity.Entity;
import utilities.Position;
import world.map.MapController;
import world.map.tiles.Tile;
import world.map.tiles.TileManager;

import java.util.*;

public class AStar
{
    private static Map<Position, Node> nodes;
    private static Position startPos;
    private static Position endPos;
    private static Node startNode;
    private static Node endNode;

    private static Position[] directions =
            {
                    new Position(0, -1),    // up
                    new Position(0, 1),     // down
                    new Position(-1, 0),    // left
                    new Position(1, 0),     // right
                    new Position(-1, -1),   // up-left
                    new Position(1, -1),    // up-right
                    new Position(-1, 1),    // down-left
                    new Position(1, 1),     // down-right
            };

    /**
     * returns path from entitySource to entityTarget
     *
     * @param entitySource
     * @param entityTarget
     * @return an array of n positions, where: P0 is starting points, Pn-1 is middle points of entityTarget's hitbox.
     * New points between P0 and Pn-1 is created, whenever there's need to make a turn to in order to
     * follow the best heuristics or avoid an obstacle such as collidable tile
     */
    public static Position[] getPathToEntity(Entity entitySource, Entity entityTarget)
    {
        ArrayList<Position> path = new ArrayList<>();

        nodes = new HashMap<>();
        startPos = entitySource.getHitbox().getCenterWorldPosition();
        endPos = entityTarget.getHitbox().getCenterWorldPosition();
        startNode = new Node(startPos, startPos, endPos, null);   // endPos and startPos in 1st argument are switched, so algorithm tends to 'hit' target from sides, not diagonals
        endNode = new Node(endPos, startPos, endPos, null);

        int hitboxWidth = entitySource.getHitbox().getWidth();
        int hitboxHeight = entitySource.getHitbox().getHeight();

        PriorityQueue<Node> openList = new PriorityQueue<>((n1, n2) ->
        {
            int fCostComparison = Float.compare(n1.getfCost(), n2.getfCost());
            if (fCostComparison != 0) return fCostComparison;
            else return Float.compare(n1.gethCost(), n2.gethCost());// Prioritize nodes with a lower hCost (more direct path towards goal)
        });
        Set<Position> closedList = new HashSet<>(); // visited nodes

        openList.add(startNode);
     //  System.out.println(Node.nodeCounter);
        Node.nodeCounter = 0;
        while (!openList.isEmpty())
        {
            Node currentNode = openList.poll();
            path.add(currentNode.getWorldPosition());     // uncomment for all nodes visualization
            closedList.add(currentNode.getWorldPosition());

            if (currentNode.equals(endNode))  // if target was reached - reconstruct path
            {
                reconstructPath(currentNode, path, hitboxWidth, hitboxHeight);
                trimPath(currentNode, path);
                path.add(endPos);
                return path.toArray(new Position[0]);
            }

            for (Position direction : directions)   // checking neighbours
            {
                Node neighbour = getNeighbour(currentNode, direction);
                if (neighbour == null || closedList.contains(neighbour.getWorldPosition()) || neighbour.isColliding())
                {
                    continue;
                }

                float cost = currentNode.getfCost();
                boolean isInOpenList = openList.contains(neighbour);

                if (isInOpenList == false || cost < neighbour.getfCost())
                {
                    neighbour.setParent(currentNode);

                    if (!isInOpenList)
                    {
                        openList.add(neighbour);
                    }
                }
                if (Node.nodeCounter > 15000) return null;
            }
        }
        //return path.toArray(new Position[0]);
        return null; // If no path is found, return null
    }


    /**
     * trims path by leaving only significant points.
     * @param currentNode node from which ancestors will be read
     * @param path collection to save reconstructed path
     */
    private static void trimPath(Node currentNode, ArrayList<Position> path)
    {
        LinkedList<Position> trimmedPath = new LinkedList<>();
        Position lastDirection = null;

        while (currentNode != null)
        {
            Position parentPosition = null;
            if (currentNode.getParent() != null)
            {
                parentPosition = currentNode.getParent().getWorldPosition();
            }

            Position currentDirection = null;
            if (parentPosition != null)
            {
                currentDirection = new Position(currentNode.getWorldPosition().x - parentPosition.x, currentNode.getWorldPosition().y - parentPosition.y);
            }

            // add point to the path only if the direction changed
            if (lastDirection == null || !lastDirection.equals(currentDirection))
            {
                trimmedPath.addFirst(currentNode.getWorldPosition());
                lastDirection = currentDirection;
            }
            currentNode = currentNode.getParent();
        }
        path.clear();
        path.addAll(trimmedPath);
    }


    private static void reconstructPath(Node currentNode, ArrayList<Position> path, int hitboxWidth, int hitboxHeight)
    {
        while (currentNode != null)
        {
            path.add(currentNode.getWorldPosition());
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);
        shiftPathToAvoidCollisions(path, hitboxWidth, hitboxHeight);
    }

    private static void shiftPathToAvoidCollisions(ArrayList<Position> path, int hitboxWidth, int hitboxHeight)
    {
        for (int i = 0; i < path.size()-1; i++) // -1 to ignore hitbox center
        {
            Position currentPos = path.get(i);
            Tile rightTile = MapController.getCurrentMap().getTile(currentPos.x + hitboxWidth, currentPos.y);
            Tile downTile = MapController.getCurrentMap().getTile(currentPos.x, currentPos.y + hitboxHeight);
            Tile diagonalRightDownTile = MapController.getCurrentMap().getTile(currentPos.x + hitboxWidth, currentPos.y + hitboxHeight);
            Tile diagonalLeftDownTile = MapController.getCurrentMap().getTile(currentPos.x - hitboxWidth, currentPos.y + hitboxHeight);

            if (rightTile.isColliding())
            {
                int offset = (currentPos.x + hitboxWidth) - rightTile.getWorldPosition().x;
                currentPos.x -= offset;
            }
            if (diagonalRightDownTile.isColliding())
            {
                int offsetX = ((currentPos.x + hitboxWidth) - diagonalRightDownTile.getWorldPosition().x)/2;
                int offsetY = ((currentPos.y + hitboxHeight) - diagonalRightDownTile.getWorldPosition().y)/2;
                currentPos.x -= offsetX;
                currentPos.y -= offsetY;
            }

            if (downTile.isColliding())
            {
                int offset = (currentPos.y + hitboxHeight) - downTile.getWorldPosition().y;
                currentPos.y -= offset;
            }
            if (diagonalLeftDownTile.isColliding())
            {
                int offsetX = ((currentPos.x - hitboxWidth) - diagonalLeftDownTile.getWorldPosition().x)/2;
                int offsetY = ((currentPos.y + hitboxHeight) - diagonalLeftDownTile.getWorldPosition().y)/2;
                currentPos.x += offsetX;
                currentPos.y -= offsetY;
            }
            path.set(i, currentPos);
        }
    }



    private static Node getNeighbour(Node sourceNode, Position side)
    {
        Position neighbourWorldPosition = new Position(sourceNode.getWorldPosition().x + (side.x*Node.NODE_SIZE), sourceNode.getWorldPosition().y + side.y*Node.NODE_SIZE);

        if (MapController.getCurrentMap().isInWorldBoundaries(neighbourWorldPosition) == false) // if "neighbour" is out of map boundaries
        {
            return null;
        }
        else if (nodes.get(neighbourWorldPosition) == null) // if neighbour did not exist in map - create new object of it
        {
            Node node = new Node(neighbourWorldPosition, startPos, endPos, sourceNode);

            if (side.x != 0 && side.y != 0) // diagonals
            {
                Position diagonalCheck1 = new Position(neighbourWorldPosition.x, sourceNode.getWorldPosition().y);
                Position diagonalCheck2 = new Position(sourceNode.getWorldPosition().x, neighbourWorldPosition.y);

                // if any of diagonals are collidable, return null
                if (MapController.getCurrentMap().getTile(diagonalCheck1).isColliding() || MapController.getCurrentMap().getTile(diagonalCheck2).isColliding())
                {
                    return null;
                }
            }

            nodes.put(node.getWorldPosition(), node);
            return node;
        }
        else
        {
            return nodes.get(neighbourWorldPosition);
        }
    }
}