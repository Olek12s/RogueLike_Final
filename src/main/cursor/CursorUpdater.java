package main.cursor;

import main.controller.Updatable;

public class CursorUpdater implements Updatable
{
    CursorHUD cursor;

    public CursorUpdater(CursorHUD cursor)
    {
        this.cursor = cursor;
        cursor.gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        updateCursorPosition();
    }

    private void updateCursorPosition()
    {
        cursor.cursorPosition.x = cursor.gc.mouseHandler.mouseX;
        cursor.cursorPosition.y = cursor.gc.mouseHandler.mouseY;
    }
}
