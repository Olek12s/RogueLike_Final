package utilities;

import main.GameController;

import java.awt.event.*;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private final GameController gc;

    public int mouseX = -1;
    public int mouseY = -1;
    public int scrollCount = 0;

    public MouseHandler(GameController gameController)
    {
        this.gc = gameController;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
        System.out.println("Mouse pressed at: " + mouseX + ", " + mouseY);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        scrollCount += e.getWheelRotation();
    }
}
