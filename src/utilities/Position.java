package utilities;

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
