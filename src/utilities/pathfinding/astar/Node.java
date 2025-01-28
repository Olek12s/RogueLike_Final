package utilities.pathfinding.astar;

import main.entity.Entity;
import utilities.Position;
import world.map.MapController;
import world.map.tiles.Tile;
import world.map.tiles.TileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Node
{
    public static int NODE_SIZE = 32;   // only binary
    private Position worldPosition;
    private float gCost;                // distance from this node to start
    private float hCost;                // distance from this node to goal
    private float fCost;                // total cost   - fCost = gCost + hCost
    //private static float greed = 0.5f;  // greed value - higher the value - higher the greed to follow path closer to the end
    private boolean isColliding;
    private Node parent;  // Parent node to track path

    public Position getWorldPosition() {return worldPosition;}

    public float getfCost() {return fCost;}
    public float gethCost() {return hCost;}
    public float getgCost() {return gCost;}

    public void setgCost(float gCost) {this.gCost = gCost;}
    public void sethCost(float hCost) {this.hCost = hCost;}
    public void setfCost(float fCost) {this.fCost = fCost;}

    public boolean isColliding() {return isColliding;}
    public Node getParent() {return parent;}  // Getter for parent
    public void setParent(Node parent) {this.parent = parent;}  // Setter for parent

    public static int nodeCounter = 0;
    public Node(Position worldPosition, Position startHitboxCenter, Position endHitboxCenter, Node parent)
    {
        Position startPosition = new Position(startHitboxCenter.x - (startHitboxCenter.x % NODE_SIZE), startHitboxCenter.y - (startHitboxCenter.y % NODE_SIZE));
        Position endPosition = new Position(endHitboxCenter.x - (endHitboxCenter.x % NODE_SIZE), endHitboxCenter.y - (endHitboxCenter.y % NODE_SIZE));

        this.worldPosition = new Position(worldPosition.x - (worldPosition.x % NODE_SIZE), worldPosition.y - (worldPosition.y % NODE_SIZE));    // for instance - in 64x64 tile and 8x8 node, there can be 64 nodes in one tile divided evenly.
        this.parent = parent;
        calculateCosts(worldPosition, startPosition, endPosition);
        this.isColliding = MapController.getCurrentMap().getTile(worldPosition).isColliding();
        nodeCounter++;
    }

    private void calculateCosts(Position nodePosition, Position startNodePosition, Position endNodePosition) {
        Tile nodeTile = MapController.getCurrentMap().getTile(worldPosition);
        float traversalCost = 1 / TileManager.getTileObject(nodeTile.getId()).getTraversalCost();  // Correct cost factor
        float greed = 1;

        this.gCost = distanceBetweenNodes(nodePosition, startNodePosition) * traversalCost; // gCost is weighted by tile cost
        this.hCost = distanceBetweenNodes(nodePosition, endNodePosition);
        if (nodeCounter > 500)
        {
            greed = 99999f;
            //System.out.println("greed");
        }
        this.fCost = gCost + (hCost * (greed));
    }

    public int distanceBetweenNodes(Position nodeSourcePosition, Position nodeTargetPosition)
    {
        int sx = nodeSourcePosition.x;
        int sy = nodeSourcePosition.y;
        int tx = nodeTargetPosition.x;
        int ty = nodeTargetPosition.y;

        int xDistance = Math.abs(sx - tx);
        int yDistance = Math.abs(sy - ty);

        return (xDistance + yDistance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return worldPosition.equals(node.worldPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldPosition);
    }
}