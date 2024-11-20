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
        MiniBitingSlime miniBitingSlime = new MiniBitingSlime(gc, new Position(-5632,-5632));

        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime);
    }
}
