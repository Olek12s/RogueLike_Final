package org.example.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    public boolean W_PRESSED = false;
    public boolean A_PRESSED = false;
    public boolean S_PRESSED = false;
    public boolean D_PRESSED = false;

    public boolean UP_PRESSED = false;
    public boolean DOWN_PRESSED = false;
    public boolean LEFT_PRESSED = false;
    public boolean RIGHT_PRESSED = false;

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        switch (code)
        {
            case KeyEvent.VK_W: W_PRESSED = true; break;
            case KeyEvent.VK_A: A_PRESSED = true; break;
            case KeyEvent.VK_S: S_PRESSED = true; break;
            case KeyEvent.VK_D: D_PRESSED = true; break;

            case KeyEvent.VK_UP: UP_PRESSED = true; break;
            case KeyEvent.VK_LEFT: LEFT_PRESSED = true; break;
            case KeyEvent.VK_DOWN: DOWN_PRESSED = true; break;
            case KeyEvent.VK_RIGHT: RIGHT_PRESSED = true; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        switch (code)
        {
            case KeyEvent.VK_W: W_PRESSED = false; break;
            case KeyEvent.VK_A: A_PRESSED = false; break;
            case KeyEvent.VK_S: S_PRESSED = false; break;
            case KeyEvent.VK_D: D_PRESSED = false; break;

            case KeyEvent.VK_UP: UP_PRESSED = false; break;
            case KeyEvent.VK_LEFT: LEFT_PRESSED = false; break;
            case KeyEvent.VK_DOWN: DOWN_PRESSED = false; break;
            case KeyEvent.VK_RIGHT: RIGHT_PRESSED = false; break;
        }
    }
}
