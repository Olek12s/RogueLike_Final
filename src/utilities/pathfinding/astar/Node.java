package utilities.pathfinding.astar;

import main.entity.Entity;
import utilities.Position;
import world.map.MapController;
import world.map.tiles.Tile;
import world.map.tiles.TileManager;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private Position position;
    private float gCost;  // heuristic cost
    private float hCost;  // cost from starting node to this node
    private float fCost;  // total cost   - fCost = gCost + hCost
    private Node parent;
    private boolean isPassable;

    public Position getPosition() {return position;}
    //public boolean isPassable() {return isPassable;}
    public float getGCost() {return fCost;}
    public Node getParent() {return parent;}
    public float getfCost() {return fCost;}
    public void setParent(Node parent) {this.parent = parent;}

    public float gethCost() {return hCost;}
    public float getgCost() {return gCost;}

    public Node(Position position, Tile tile)
    {
        this.position = position;
        boolean isCollidable = TileManager.getTileObject(tile.getId()).isCollidable();
        this.isPassable = !isCollidable;
    }


    public void calculateCosts(Node startNode, Node endNode, Tile tile)
    {
        float traversalCost = 1 / TileManager.getTileObject(tile.getId()).getTraversalCost();
        this.gCost = startNode.gCost + traversalCost;
        this.hCost = (Math.abs(position.x - endNode.position.x) + Math.abs(position.y - endNode.position.y)) * traversalCost; // Manhattan distance
        this.fCost = this.gCost + this.hCost;
    }

    public static List<Node> getNeighbors(Node node)
    {
        List<Node> neighbors = new ArrayList<>();
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

        for (Position direction : directions)
        {
            Position neighborPos = new Position(node.getPosition().x + direction.x, node.getPosition().y + direction.y);
            Tile tile = MapController.getCurrentMap().getTile(neighborPos);

            if (tile != null)
            {
                Node neighbor = new Node(neighborPos, tile);
                if (neighbor.isPassable())
                {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    public boolean isPassable()
    {
        return isPassable;
    }
}
