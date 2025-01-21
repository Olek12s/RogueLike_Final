package utilities.pathfinding.astar;

import utilities.Position;
import world.map.tiles.Tile;
import world.map.tiles.TileManager;

public class Node
{
    private Position position;
    private int gCost;
    private int hCost;
    private int fCost;
    private Node parent;
    private boolean isPassable;

    public Position getPosition() {return position;}
    public boolean isPassable() {return isPassable;}

    public Node(Position position, Tile tile)
    {
        this.position = position;

        boolean isCollidable = TileManager.getTileObject(tile.getId()).isCollidable();
        this.isPassable = !isCollidable;
    }

    public void calculateCosts(Node startNode, Node endNode, Tile tile)
    {
        float traversalCost = TileManager.getTileObject(tile.getId()).getTraversalCost();

        this.gCost = (int) ((Math.abs(position.x - startNode.position.x) + Math.abs(position.y - startNode.position.y)) * traversalCost);
        this.hCost = Math.abs(position.x - endNode.position.x) + Math.abs(position.y - endNode.position.y);
        this.fCost = this.gCost + this.hCost;
    }
}
