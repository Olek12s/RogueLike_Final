package utilities.camera;


import main.Updatable;

public class CameraUpdater implements Updatable
{
    private Camera camera;

    public CameraUpdater(Camera camera)
    {
        this.camera = camera;
    }

    @Override
    public void update()    // by default - focus is on player
    {
        camera.focusOn(camera.gc.player);
    }
}
