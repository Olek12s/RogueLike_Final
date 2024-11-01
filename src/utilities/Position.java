package utilities;

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

    public void add(Position position)
    {
        this.x = x + position.x;
        this.y = x + position.y;
    }
    public void subtract(Position position)
    {
        this.x = x - position.x;
        this.y = x - position.y;
    }
}
