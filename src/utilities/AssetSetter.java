package utilities;

import main.GameController;
import main.entity.bitingSlime.MiniBitingSlime;

public class AssetSetter
{
    GameController gc;

    public AssetSetter(GameController gc)
    {
        this.gc = gc;
        setAssets();
    }

    public void setAssets()
    {
        MiniBitingSlime miniBitingSlime = new MiniBitingSlime(gc, new Position(500, 500), new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime"), 48));

        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime);
    }
}
