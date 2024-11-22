package main.cursor;

import main.GameController;
import utilities.Position;

public class CursorHUD
{
    GameController gc;
    Position cursorPosition;
    protected CursorRenderer cursorRenderer;
    protected CursorUpdater cursorUpdater;

    public CursorHUD(GameController gc)
    {
        this.gc = gc;
        cursorPosition = new Position(gc.getWidth(), gc.getHeight());
        cursorRenderer = new CursorRenderer(this);
        cursorUpdater = new CursorUpdater(this);
    }
}
