package utilities;

import main.Direction;
import main.controller.GameController;
import utilities.camera.Camera;

import java.util.Objects;

public class Position
{
    public int x;
    public int y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    public static Position screenToWorldPosition(int x, int y)
    {
        int width = GameController.getInstance().getWidth();
        int height = GameController.getInstance().getHeight();
        double scaleFactor = Camera.getScaleFactor();

        int worldX = (int)((x - width / 2) / scaleFactor + GameController.getInstance().camera.getCameraPosition().x);
        int worldY = (int)((y - height / 2) / scaleFactor + GameController.getInstance().camera.getCameraPosition().y);

        return new Position(worldX, worldY);
    }

    public float distanceTo(Position other)
    {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /** Calculates and returns the counter direction based on the positions of target and source.
     * Examples:
     *
     * If target is above source -> method returns DOWN
     * if target is below source -> method returns UP
     * If target is in the TOP-LEFT of source -> method returns DOWN-RIGHT
     * If target is on the LEFT of source -> method returns RIGHT
     * @param target
     * @param source
     * @return
     */
    public static Direction getCounterDirection(Position target, Position source)
    {
        double dx = target.x - source.x;
        double dy = target.y - source.y;
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        if (angle >= -22.5 && angle < 22.5) {
            return Direction.RIGHT;
        } else if (angle >= 22.5 && angle < 67.5) {
            return Direction.DOWN_RIGHT;
        } else if (angle >= 67.5 && angle < 112.5) {
            return Direction.DOWN;
        } else if (angle >= 112.5 && angle < 157.5) {
            return Direction.DOWN_LEFT;
        } else if (angle >= -67.5 && angle < -22.5) {
            return Direction.UP_RIGHT;
        } else if (angle >= -112.5 && angle < -67.5) {
            return Direction.UP;
        } else if (angle >= -157.5 && angle < -112.5) {
            return Direction.UP_LEFT;
        } else {
            return Direction.LEFT;
        }
    }

    @Override
    public String toString()
    {
        return "["+ x + " " + y + "]";
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Position pos = (Position) obj;
        return x == pos.x && y == pos.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
}
