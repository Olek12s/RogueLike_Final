package main.entity.npc;

import main.Direction;
import main.GameController;
import main.entity.Entity;
import utilities.DrawPriorities;
import utilities.Position;

import java.awt.*;

public class BitingSlime extends Entity
{
    GameController gc;
    private int randomDirectionCounter = 0;

    public BitingSlime(GameController gc)
    {
        super(gc);
        this.gc = gc;
        gc.drawables.add(this);
        gc.updatables.add(this);

        //STATISTICS
        name = "Biting Slime";
        hitPoints = 10;
        maxHitPoints = hitPoints;
        setMovementSpeed(0);
        isMoving = true;
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Entity.value;}

    public void draw(Graphics2D g2)
    {
        super.draw(g2);
    }

    @Override
    public void update()
    {
        super.update();
        updateRandomDirection();
    }

    private void updateRandomDirection()
    {
        if (randomDirectionCounter >= gc.getTargetLogicFrame() * 1) // once per second
        {
            int randomNum = (int) (Math.random() * 8);
            switch (randomNum)
            {
                /*
                case 0: direction = Direction.DOWN;break;
                case 1: direction = Direction.UP;break;
                case 2: direction = Direction.LEFT;break;
                case 3: direction = Direction.RIGHT;break;
                case 4: direction = Direction.UP_LEFT;break;
                case 5: direction = Direction.UP_RIGHT;break;
                case 6: direction = Direction.DOWN_LEFT;break;
                case 7: direction = Direction.DOWN_RIGHT;break;
                
                 */
            }
            randomDirectionCounter = 0;
        }
        else
        {
            randomDirectionCounter++;
        }
    }
}
