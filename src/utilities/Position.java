package utilities;

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
