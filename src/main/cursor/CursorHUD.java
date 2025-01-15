package main.cursor;

import main.controller.GameController;
import utilities.Position;
import utilities.camera.Camera;

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

    public int getAngle(Position referencedPosition)
    {
        Position cameraPosition = gc.camera.getCameraPosition();
        double scaleFactor = Camera.getScaleFactor();

        int worldX = (int) ((cursorPosition.x - (double) gc.getWidth() / 2) / scaleFactor + cameraPosition.x);
        int worldY = (int) ((cursorPosition.y - (double) gc.getHeight() / 2) / scaleFactor + cameraPosition.y);

        int deltaX = worldX - referencedPosition.x;
        int deltaY = worldY - referencedPosition.y;

        double angleInRadians = Math.atan2(deltaY, deltaX);
        int angleInDegrees = (int) Math.toDegrees(angleInRadians);

        angleInDegrees = (angleInDegrees + 90) % 360;   // moving angle by 90 degrees, so 0 degrees stays at the top, not right

        if (angleInDegrees < 0)
        {
            angleInDegrees += 360;
        }

        return angleInDegrees;
    }
}
