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

    public void centerOnEntity(Entity entity)
    {
        cameraPosition.x = entity.getPosition().x;
        cameraPosition.y = entity.getPosition().y;
    }

    @Override
    public void update()
    {
        centerOnEntity(gc.player);
        System.out.println(cameraPosition);
    }
}
