package utilities;

import main.controller.GameController;

import java.awt.event.*;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private final GameController gc;

    private int mouseX = -1;
    private int mouseY = -1;
    public int scrollCount = 0;

    public boolean leftButtonPressed = false;

    public Position getClickPosition() {return new Position(mouseX, mouseY);}

    public int getMouseX() {return mouseX;}
    public int getMouseY() {return mouseY;}

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
        Position cameraPosition = gc.camera.getCameraPosition();

        System.out.println("mouse clicked at: [" + mouseX + ", " + mouseY + "] Angle: " + gc.cursor.getAngle(cameraPosition));

        int code = e.getButton();
        switch(code)
        {
            case MouseEvent.BUTTON1: leftButtonPressed = true; break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        int code = e.getButton();
        switch(code)
        {
            case MouseEvent.BUTTON1: leftButtonPressed = false; break;
        }
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
