package utilities;

import main.controller.GameController;
import utilities.camera.Camera;

import java.awt.event.*;

public class KeyHandler implements KeyListener
{
    GameController gc;

    public boolean W_PRESSED = false;
    public boolean A_PRESSED = false;
    public boolean S_PRESSED = false;
    public boolean D_PRESSED = false;

    public boolean UP_PRESSED = false;
    public boolean DOWN_PRESSED = false;
    public boolean LEFT_PRESSED = false;
    public boolean RIGHT_PRESSED = false;
    public boolean TIDE_PRESSED = false;
    public boolean SHIFT_PRESSED = false;

    public boolean MULTIPLE_PRESSED = false;

    public boolean I_PRESSED = false;
    public boolean E_PRESSED = false;
    public boolean F_PRESSED = false;
    public boolean C_PRESSED = false;
    public boolean SPACE_PRESSED = false;

    public boolean CTRL_PRESSED = false;

    public boolean ONE_PRESSED = false;
    public boolean TWO_PRESSED = false;
    public boolean THREE_PRESSED = false;
    public boolean FOUR_PRESSED = false;
    public boolean FIVE_PRESSED = false;
    public boolean SIX_PRESSED = false;
    public boolean SEVEN_PRESSED = false;
    public boolean EIGHT_PRESSED = false;
    public boolean NINE_PRESSED = false;
    public boolean PLUS_PRESSED = false;


    public KeyHandler(GameController gc)
    {
        this.gc = gc;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

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

            case KeyEvent.VK_BACK_QUOTE: TIDE_PRESSED = true; break;
            case KeyEvent.VK_SHIFT: SHIFT_PRESSED = true; break;

            case KeyEvent.VK_MULTIPLY: MULTIPLE_PRESSED = true; break;

            case KeyEvent.VK_I: I_PRESSED = true; break;
            case KeyEvent.VK_E: E_PRESSED = true; break;
            case KeyEvent.VK_F: F_PRESSED = true; break;
            case KeyEvent.VK_C: C_PRESSED = true; break;
            case KeyEvent.VK_SPACE: SPACE_PRESSED = true; break;

            case KeyEvent.VK_CONTROL: CTRL_PRESSED = true; break;

            case KeyEvent.VK_1: ONE_PRESSED = true; break;
            case KeyEvent.VK_2: TWO_PRESSED = true; break;
            case KeyEvent.VK_3: THREE_PRESSED = true; break;
            case KeyEvent.VK_4: FOUR_PRESSED = true; break;
            case KeyEvent.VK_5: FIVE_PRESSED = true; break;
            case KeyEvent.VK_6: SIX_PRESSED = true; break;
            case KeyEvent.VK_7: SEVEN_PRESSED = true; break;
            case KeyEvent.VK_8: EIGHT_PRESSED = true; break;
            case KeyEvent.VK_9: NINE_PRESSED = true; break;
            case KeyEvent.VK_ADD: PLUS_PRESSED = true; break;
        }
        if  (TIDE_PRESSED == true) gc.setDebugMode(!gc.isDebugMode());
        if (MULTIPLE_PRESSED == true) Camera.setScaleFactor(1);
        if (PLUS_PRESSED)   // debug
        {
            gc.player.statistics.setHitPoints(gc.player.statistics.getMaxHitPoints());
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

            case KeyEvent.VK_BACK_QUOTE: TIDE_PRESSED = false; break;
            case KeyEvent.VK_SHIFT: SHIFT_PRESSED = false; break;

            case KeyEvent.VK_MULTIPLY: MULTIPLE_PRESSED = false; break;

            case KeyEvent.VK_I: I_PRESSED = false; break;
            case KeyEvent.VK_E: E_PRESSED = false; break;
            case KeyEvent.VK_F: F_PRESSED = false; break;
            case KeyEvent.VK_C: C_PRESSED = false; break;
            case KeyEvent.VK_SPACE: SPACE_PRESSED = false; break;

            case KeyEvent.VK_CONTROL: CTRL_PRESSED = false; break;

            case KeyEvent.VK_1: ONE_PRESSED = false; break;
            case KeyEvent.VK_2: TWO_PRESSED = false; break;
            case KeyEvent.VK_3: THREE_PRESSED = false; break;
            case KeyEvent.VK_4: FOUR_PRESSED = false; break;
            case KeyEvent.VK_5: FIVE_PRESSED = false; break;
            case KeyEvent.VK_6: SIX_PRESSED = false; break;
            case KeyEvent.VK_7: SEVEN_PRESSED = false; break;
            case KeyEvent.VK_8: EIGHT_PRESSED = false; break;
            case KeyEvent.VK_9: NINE_PRESSED = false; break;
            case KeyEvent.VK_ADD: PLUS_PRESSED = false; break;
        }
    }
}
