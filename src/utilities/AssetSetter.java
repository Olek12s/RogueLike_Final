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
        int v = 500;
        MiniBitingSlime miniBitingSlime1 = new MiniBitingSlime(gc, new Position(-5632+v,-5632+v));
        MiniBitingSlime miniBitingSlime2 = new MiniBitingSlime(gc, new Position(-5600+v,-5632+v));
        MiniBitingSlime miniBitingSlime3 = new MiniBitingSlime(gc, new Position(-5550+v,-5632+v));
        MiniBitingSlime miniBitingSlime4 = new MiniBitingSlime(gc, new Position(-5632+v,-5600+v));
        MiniBitingSlime miniBitingSlime5 = new MiniBitingSlime(gc, new Position(-5632+v,-5500+v));
        MiniBitingSlime miniBitingSlime6 = new MiniBitingSlime(gc, new Position(-5232+v,-5200+v));
        MiniBitingSlime miniBitingSlime7 = new MiniBitingSlime(gc, new Position(-5432+v,-5400+v));

        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime1);
        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime2);
        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime3);
        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime4);
        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime5);
        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime6);
        gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities().add(miniBitingSlime7);
    }
}
