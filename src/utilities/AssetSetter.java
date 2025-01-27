package utilities;

import main.controller.GameController;
import main.entity.Entity;
import main.entity.monster.*;
import main.item.armor.WoodenBoots;
import main.item.armor.WoodenShield;
import main.item.potion.energy.LargeEnergyPotion;
import main.item.potion.energy.MediumEnergyPotion;
import main.item.potion.energy.SmallEnergyPotion;
import main.item.potion.health.LargeHealthPotion;
import main.item.potion.health.MediumHealthPotion;
import main.item.potion.health.SmallHealthPotion;
import main.item.potion.mana.LargeManaPotion;
import main.item.potion.mana.MediumManaPotion;
import main.item.potion.mana.SmallManaPotion;
import main.item.tool.WoodenPickaxe;

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
        testEntity1 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x - 50, gc.player.getWorldPosition().y - 40));
        //testEntity2 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x-25, gc.player.getWorldPosition().y - 50));
        //testEntity3 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x, gc.player.getWorldPosition().y - 40));
        //testEntitySmallSlime = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x-150, gc.player.getWorldPosition().y + 80));
        //testEntitySlime = new BitingSlime(gc, new Position(gc.player.getWorldPosition().x-100, gc.player.getWorldPosition().y + 80));
        //testEntitySpider = new Spider(gc, new Position(gc.player.getWorldPosition().x-50, gc.player.getWorldPosition().y + 80));
        //testEntityVenomousSpider = new VenomousSpider(gc, new Position(gc.player.getWorldPosition().x, gc.player.getWorldPosition().y + 80));
        //testEntityZombie = new Zombie(gc, new Position(gc.player.getWorldPosition().x+50, gc.player.getWorldPosition().y + 80));


        for (int i = 0; i <  0; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x + (i * 25), gc.player.getWorldPosition().y + (j * 50)));
            }
        }



        SmallHealthPotion shp = new SmallHealthPotion(gc, new Position(startX+32, startY));
        MediumHealthPotion mhp = new MediumHealthPotion(gc, new Position(startX+64, startY));
        LargeHealthPotion lhp = new LargeHealthPotion(gc, new Position(startX+96, startY));

        SmallManaPotion smp = new SmallManaPotion(gc, new Position(startX+32, startY+64));
        MediumManaPotion mmp = new MediumManaPotion(gc, new Position(startX+64, startY+64));
        LargeManaPotion lmp = new LargeManaPotion(gc, new Position(startX+96, startY+64));

        SmallEnergyPotion sep = new SmallEnergyPotion(gc, new Position(startX+32, startY+64*2));
        MediumEnergyPotion mep = new MediumEnergyPotion(gc, new Position(startX+64, startY+64*2));
        LargeEnergyPotion lep = new LargeEnergyPotion(gc, new Position(startX+96, startY+64*2));

        WoodenShield shield = new WoodenShield(gc, new Position(startX-60, startY));
        WoodenBoots boots = new WoodenBoots(gc, new Position(startX-120, startY));
        WoodenPickaxe pickaxe = new WoodenPickaxe(gc, new Position(startX-150, startY));

        for (int i = 0; i < 600; i++)
        {
            new SmallHealthPotion(gc, new Position(startX+(20*i), startY));
        }

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
