package utilities.camera;


import main.controller.Updatable;
import utilities.Position;
import world.map.Chunk;
import world.map.MapRenderer;
import world.map.tiles.Tile;

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
        camera.checkScroll();
        updateRenderDistance();
        Position.screenToWorldPosition(0, 0);
    }

    private void updateRenderDistance()
    {
        int windowWidth = camera.gc.getWidth();
        int windowHeight = camera.gc.getHeight();
        double scaleFactor = Camera.getScaleFactor();

        double visibleWidth = windowWidth / scaleFactor;
        double visibleHeight = windowHeight / scaleFactor;

        int chunkPixelSize = Chunk.getChunkSize() * Tile.tileSize;

        int chunksX = (int) Math.ceil(visibleWidth / chunkPixelSize);
        int chunksY = (int) Math.ceil(visibleHeight / chunkPixelSize);

        int calculatedRenderDistance = Math.max(chunksX, chunksY) / 2 + 1;

        MapRenderer.chunkRenderDistance = calculatedRenderDistance;
    }
}
