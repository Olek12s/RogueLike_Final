package main;

import main.entity.Entity;
import utilities.Position;

public class Camera implements Updatable
{
    public GameController gc;
    public Position cameraPosition;

    public Camera(GameController gc)
    {
        gc.updatables.add(this);
        this.gc = gc;
        this.cameraPosition = new Position(gc.getWidth()/2, gc.getHeight()/2);  // camera always on the middle of the screen
    }

    /*
        Calculating world coordinates to screen coordinates
    */
    public Position applyCameraOffset(int worldX, int worldY)
    {
        // object's world cooridanets - camera position"
        int screenX = worldX - (cameraPosition.x - gc.getWidth() / 2);
        int screenY = worldY - (cameraPosition.y - gc.getHeight() / 2);

        return new Position(screenX, screenY);
    }

    /**
     * sets the camera's focus to a given position. Updates every tick.
    *
    * @Param Position position
    */

    public void focusOn(Entity entity)
    {
        cameraPosition.x = entity.getWorldPosition().x + entity.currentSprite.resolution/2;
        cameraPosition.y = entity.getWorldPosition().y + entity.currentSprite.resolution/2;
    }

    @Override
    public void update()
    {
        focusOn(gc.player);
    }
}
