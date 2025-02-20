package utilities;

import main.controller.GameController;
import main.entity.Entity;
import main.entity.monster.*;
import main.item.ItemID;
import main.item.armor.amulet.GelAmulet;
import main.item.armor.boots.WoodenBoots;
import main.item.armor.chestplate.WoodenChestplate;
import main.item.armor.helemt.WoodenHelmet;
import main.item.armor.pants.WoodenPants;
import main.item.armor.ring.GelRing;
import main.item.armor.shield.Shield;
import main.item.armor.shield.WoodenShield;
import main.item.ingredients.*;
import main.item.potion.energy.LargeEnergyPotion;
import main.item.potion.energy.MediumEnergyPotion;
import main.item.potion.energy.SmallEnergyPotion;
import main.item.potion.health.LargeHealthPotion;
import main.item.potion.health.MediumHealthPotion;
import main.item.potion.health.SmallHealthPotion;
import main.item.potion.mana.LargeManaPotion;
import main.item.potion.mana.MediumManaPotion;
import main.item.potion.mana.SmallManaPotion;
import main.item.tool.axe.WoodenAxe;
import main.item.tool.pickaxe.WoodenPickaxe;
import main.item.weapon.sword.LongWoodenSword;
import main.item.weapon.sword.WoodenSword;

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
        testEntity2 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x-25, gc.player.getWorldPosition().y - 50));

        //testEntity3 = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x, gc.player.getWorldPosition().y - 40));
        //testEntitySmallSlime = new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x-150, gc.player.getWorldPosition().y + 80));
        //testEntitySlime = new BitingSlime(gc, new Position(gc.player.getWorldPosition().x-100, gc.player.getWorldPosition().y + 80));
        //testEntitySpider = new Spider(gc, new Position(gc.player.getWorldPosition().x-50, gc.player.getWorldPosition().y + 80));
        //testEntityVenomousSpider = new VenomousSpider(gc, new Position(gc.player.getWorldPosition().x, gc.player.getWorldPosition().y + 80));
        //testEntityZombie = new Zombie(gc, new Position(gc.player.getWorldPosition().x+50, gc.player.getWorldPosition().y + 80));

        //gc.player.getInventory().getBeltSlots()[0].setStoredItem(new WoodenShield(gc));
        //gc.player.getInventory().getBeltSlots()[1].setStoredItem(new MediumEnergyPotion(gc));
        //gc.player.getInventory().getBeltSlots()[2].setStoredItem(new WoodenBoots(gc));
        //gc.player.getInventory().getBeltSlots()[4].setStoredItem(new WoodenPickaxe(gc));
        gc.player.getInventory().getBeltSlots()[5].setStoredItem(new WoodenShield(gc));

        //gc.player.getInventory().getHelmetSlot().setStoredItem(new WoodenPickaxe(gc));
        //gc.player.getInventory().getChestplateSlot().setStoredItem(new LargeManaPotion(gc));
        //gc.player.getInventory().getBootsSlot().setStoredItem(new WoodenShield(gc));;

        new Wood(gc, new Position(startX-(120), startY+80));
        new Coal(gc, new Position(startX-(120), startY+100));
        new IronOre(gc, new Position(startX-(120), startY+120));
        new Diamond(gc, new Position(startX-(120), startY+140));
        new Ruby(gc, new Position(startX-(120), startY+160));
        new Slime(gc, new Position(startX-(120), startY+180));

        for (int i = 0; i < 30; i++)
        {
            new RedFlower(gc, new Position(startX+(20*i), startY+50));
        }
        for (int i = 0; i < 30; i++)
        {
            new BlueFlower(gc, new Position(startX+(20*i), startY+100));
        }

        for (int i = 0; i < 30; i++)
        {
            new YellowFlower(gc, new Position(startX+(20*i), startY+150));
        }


        for (int i = 0; i < 0; i++)
        {
            for (int j = 0; j < 10; j++)
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

        WoodenHelmet wh = new WoodenHelmet(gc, new Position(startX-50, startY));
        WoodenChestplate cp = new WoodenChestplate(gc, new Position(startX-80, startY));
        WoodenPants wp = new WoodenPants(gc, new Position(startX-120, startY));
        WoodenBoots wb = new WoodenBoots(gc, new Position(startX-50, startY-30));
        GelAmulet gg = new GelAmulet(gc, new Position(startX-50, startY-60));
        GelRing rr1 = new GelRing(gc, new Position(startX-50, startY-90));
        GelRing rr2 = new GelRing(gc, new Position(startX-50, startY-100));
        WoodenShield sh = new WoodenShield(gc, new Position(startX-50, startY-120));

        WoodenSword sw1 = new WoodenSword(gc, new Position(startX-50, startY+30));
        LongWoodenSword sw2 = new LongWoodenSword(gc, new Position(startX-50, startY+60));



        WoodenPickaxe pickaxe = new WoodenPickaxe(gc, new Position(startX-150, startY));
        WoodenAxe axe = new WoodenAxe(gc, new Position(startX-20, startY));

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
