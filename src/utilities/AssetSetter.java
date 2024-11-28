package utilities;

import main.GameController;
import main.entity.Entity;
import main.entity.bitingSlime.MiniBitingSlime;

import java.util.List;

public class AssetSetter
{
    GameController gc;
    public static Entity testEntity;

    public AssetSetter(GameController gc)
    {
        this.gc = gc;
        setAssets();
    }

    public void setAssets()
    {
        int v = 500;
        int startX = -5632 + v;
        int startY = -6032 + v;
        int offset = 50;

        testEntity = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x - 50, gc.player.getWorldPosition().y - 50));

        List<Entity> currentChunkEntities = gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities();

        for (int i = 0; i < 50; i++)
        {
            int x = startX + (i % 40) * offset;
            int y = startY + (i / 5) * offset;
            // temp solution:

            if (gc.mapController.getCurrentMap().getTile(x, y).isColliding() == false)
            {
                if (gc.mapController.getCurrentMap().getTile(x+22, y).isColliding() == false)
                {
                    if (gc.mapController.getCurrentMap().getTile(x, y+22).isColliding() == false)
                    {
                        if (gc.mapController.getCurrentMap().getTile(x+22, y+22).isColliding() == false)
                        {
                            MiniBitingSlime miniBitingSlime = new MiniBitingSlime(gc, new Position(x, y));
                            currentChunkEntities.add(miniBitingSlime);
                        }
                    }

                }
            }
        }
    }

}
