package utilities.camera;

import main.GameController;
import main.Updatable;
import main.entity.Entity;
import utilities.AssetSetter;
import utilities.Position;


public class Camera
{
    public GameController gc;
    private CameraUpdater updater = new CameraUpdater(this);

    private Position cameraPosition;
    private static double scaleFactor = 1;

    public Camera(GameController gc)
    {
        gc.updatables.add(this.updater);
        this.gc = gc;
        this.cameraPosition = new Position(gc.getWidth()/2, gc.getHeight()/2);  // camera always on the middle of the screen
    }

    public Position getCameraPosition() {return cameraPosition;}
    public static double getScaleFactor() {return scaleFactor;}

    public Position applyCameraOffset(int worldX, int worldY)
    {
        // object's world cooridanets - camera position"
        int screenX = (int)((worldX - cameraPosition.x) * scaleFactor + gc.getWidth() / 2);
        int screenY = (int)((worldY - cameraPosition.y) * scaleFactor + gc.getHeight() / 2);

        return new Position(screenX, screenY);
    }

    public void focusOn(Entity entity)
    {
        cameraPosition.x = entity.getWorldPosition().x + entity.getCurrentSprite().resolutionX/2;
        cameraPosition.y = entity.getWorldPosition().y + entity.getCurrentSprite().resolutionY/2;
        //cameraPosition.x = AssetSetter.testEntity.getWorldPosition().x;
        //cameraPosition.y = AssetSetter.testEntity.getWorldPosition().y;
        //cameraPosition.x = -3000;
        //cameraPosition.y = -3000;
    }

    public void checkScroll()
    {
        if (gc.mouseHandler.scrollCount != 0)
        {
            if (gc.mouseHandler.scrollCount > 0)
            {
                //scaleFactor = Math.max(0.05, scaleFactor - 0.05); // further
                scaleFactor *= 0.95;
            }
            else if (gc.mouseHandler.scrollCount < 0)
            {
                //scaleFactor = Math.min(4, scaleFactor + 0.15);  // closer
                scaleFactor *= 1.05;
            }
            gc.mouseHandler.scrollCount = 0;
        }
    }
}
