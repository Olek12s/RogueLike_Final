package main.cursor;

import main.DrawPriorities;
import main.Drawable;

import java.awt.*;

public class CursorRenderer implements Drawable
{
    CursorHUD cursor;

    public CursorRenderer(CursorHUD cursor)
    {
        this.cursor = cursor;
        cursor.gc.drawables.add(this);
    }


    @Override
    public int getDrawPriority()
    {
        return DrawPriorities.Cursor.value;
    }

    @Override
    public void draw(Graphics g2)
    {
        if (cursor.cursorPosition.x >= 0 && cursor.cursorPosition.y >= 0)
        {
            g2.setColor(Color.LIGHT_GRAY);

            // Vertical lines
            g2.fillRect(cursor.cursorPosition.x - 1, cursor.cursorPosition.y - 10, 3, 21);

            // Horizontal lines
            g2.fillRect(cursor.cursorPosition.x - 10, cursor.cursorPosition.y - 1, 21, 3);
        }
    }
}
