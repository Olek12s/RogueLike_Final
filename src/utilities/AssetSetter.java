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
        //testEntity1 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x - 50, gc.player.getWorldPosition().y - 40));
        //testEntity2 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x-25, gc.player.getWorldPosition().y - 50));
        //testEntity3 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x, gc.player.getWorldPosition().y - 40));
        //testEntitySmallSlime = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x-150, gc.player.getWorldPosition().y + 80));
        //testEntitySlime = new BitingSlime(gc, new Position(gc.player.getWorldPosition().x-100, gc.player.getWorldPosition().y + 80));
        //testEntitySpider = new Spider(gc, new Position(gc.player.getWorldPosition().x-50, gc.player.getWorldPosition().y + 80));
        //testEntityVenomousSpider = new VenomousSpider(gc, new Position(gc.player.getWorldPosition().x, gc.player.getWorldPosition().y + 80));
        //testEntityZombie = new Zombie(gc, new Position(gc.player.getWorldPosition().x+50, gc.player.getWorldPosition().y + 80));



        List<Entity> currentChunkEntities = gc.mapController.getCurrentMap().getChunk(gc.player.getWorldPosition()).getEntities();

        for (int i = 0; i < 0; i++)
        {
            int x = startX + (i % 90) * offset;
            int y = startY + (i / 8) * offset;
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
