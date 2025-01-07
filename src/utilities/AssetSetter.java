package utilities;

import main.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.bitingSlime.*;

import java.util.List;

public class AssetSetter
{
    GameController gc;
    public static Entity testEntity1;
    public static Entity testEntity2;
    public static Entity testEntity3;
    public static Entity testEntitySmallSlime;
    public static Entity testEntitySlime;
    public static Entity testEntitySpider;
    public static Entity testEntityVenomousSpider;
    public static Entity testEntityZombie;

    public AssetSetter(GameController gc)
    {
        this.gc = gc;
        setAssets();
    }

    public void setAssets()
    {
        int v = 500;
        int startX = gc.player.getWorldPosition().x;
        int startY = gc.player.getWorldPosition().y;
        int offset = 50;

        //gc.mapController.getCurrentMap().safeSpawnEntityOnMap(new MiniBitingSlime(gc, new Position(20, 20)));
       



        List<Entity> currentChunkEntities = gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities();

        for (int i = 0; i < 0; i++)
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
