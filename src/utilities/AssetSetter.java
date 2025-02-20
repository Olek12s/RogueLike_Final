package utilities;

import main.controller.GameController;
import main.entity.Entity;
import main.entity.monster.*;
import main.item.ItemID;
import main.item.armor.amulet.GelAmulet;
import main.item.armor.boots.DiamondBoots;
import main.item.armor.boots.IronBoots;
import main.item.armor.boots.RubyBoots;
import main.item.armor.boots.WoodenBoots;
import main.item.armor.chestplate.DiamondChestplate;
import main.item.armor.chestplate.IronChestplate;
import main.item.armor.chestplate.RubyChestplate;
import main.item.armor.chestplate.WoodenChestplate;
import main.item.armor.helemt.DiamondHelmet;
import main.item.armor.helemt.IronHelmet;
import main.item.armor.helemt.RubyHelmet;
import main.item.armor.helemt.WoodenHelmet;
import main.item.armor.pants.DiamondPants;
import main.item.armor.pants.IronPants;
import main.item.armor.pants.RubyPants;
import main.item.armor.pants.WoodenPants;
import main.item.armor.ring.GelRing;
import main.item.armor.shield.*;
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
import main.item.weapon.sword.*;

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

        //gc.player.getInventory().getBeltSlots()[0].setStoredItem(new WoodenShield(gc));
        //gc.player.getInventory().getBeltSlots()[1].setStoredItem(new MediumEnergyPotion(gc));
        //gc.player.getInventory().getBeltSlots()[2].setStoredItem(new WoodenBoots(gc));
        //gc.player.getInventory().getBeltSlots()[4].setStoredItem(new WoodenPickaxe(gc));
        //gc.player.getInventory().getBeltSlots()[5].setStoredItem(new WoodenShield(gc));

        //gc.player.getInventory().getHelmetSlot().setStoredItem(new WoodenPickaxe(gc));
        //gc.player.getInventory().getChestplateSlot().setStoredItem(new LargeManaPotion(gc));
        //gc.player.getInventory().getBootsSlot().setStoredItem(new WoodenShield(gc));;

        //new MiniBitingSlime(gc, new Position(gc.player.getWorldPosition().x - 50, gc.player.getWorldPosition().y - 40));
        //new BitingSlime(gc, new Position(gc.player.getWorldPosition().x - 100, gc.player.getWorldPosition().y - 40));
        //new Spider(gc, new Position(gc.player.getWorldPosition().x - 150, gc.player.getWorldPosition().y - 40));
        //new VenomousSpider(gc, new Position(gc.player.getWorldPosition().x - 200, gc.player.getWorldPosition().y - 40));
        //new Zombie(gc, new Position(gc.player.getWorldPosition().x - 250, gc.player.getWorldPosition().y - 40));
        new Boss(gc, new Position(gc.player.getWorldPosition().x - 300, gc.player.getWorldPosition().y - 40));

        //gc.player.getInventory().getHelmetSlot().setStoredItem(new WoodenHelmet(gc));
        //gc.player.getInventory().getChestplateSlot().setStoredItem(new WoodenChestplate(gc));
        //gc.player.getInventory().getPantsSlot().setStoredItem(new WoodenPants(gc));
        //gc.player.getInventory().getBootsSlot().setStoredItem(new WoodenBoots(gc));
        //gc.player.getInventory().getShieldSlot().setStoredItem(new WoodenShield(gc));

        //gc.player.getInventory().getHelmetSlot().setStoredItem(new IronHelmet(gc));
        //gc.player.getInventory().getChestplateSlot().setStoredItem(new IronChestplate(gc));
        //gc.player.getInventory().getPantsSlot().setStoredItem(new IronPants(gc));
        //gc.player.getInventory().getBootsSlot().setStoredItem(new IronBoots(gc));
        //gc.player.getInventory().getShieldSlot().setStoredItem(new IronShield(gc));


        //gc.player.getInventory().getHelmetSlot().setStoredItem(new DiamondHelmet(gc));
        //gc.player.getInventory().getChestplateSlot().setStoredItem(new DiamondChestplate(gc));
        //gc.player.getInventory().getPantsSlot().setStoredItem(new DiamondPants(gc));
        //gc.player.getInventory().getBootsSlot().setStoredItem(new DiamondBoots(gc));
        //gc.player.getInventory().getShieldSlot().setStoredItem(new DiamondShield(gc));


        gc.player.getInventory().getHelmetSlot().setStoredItem(new RubyHelmet(gc));
        gc.player.getInventory().getChestplateSlot().setStoredItem(new RubyChestplate(gc));
        gc.player.getInventory().getPantsSlot().setStoredItem(new RubyPants(gc));
        gc.player.getInventory().getBootsSlot().setStoredItem(new RubyBoots(gc));
        gc.player.getInventory().getShieldSlot().setStoredItem(new RubyShield(gc));

        gc.player.getInventory().getBeltSlots()[0].setStoredItem(new WoodenSword(gc));
        gc.player.getInventory().getBeltSlots()[1].setStoredItem(new IronSword(gc));
        gc.player.getInventory().getBeltSlots()[2].setStoredItem(new DiamondSword(gc));
        gc.player.getInventory().getBeltSlots()[3].setStoredItem(new RubySword(gc));


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

        new GelAmulet(gc, new Position(startX-50, startY-60));
        new GelRing(gc, new Position(startX-50, startY-90));
        new GelRing(gc, new Position(startX-50, startY-100));

        new WoodenHelmet(gc, new Position(startX+50, startY+300));
        new WoodenChestplate(gc, new Position(startX+80, startY+300));
        new WoodenPants(gc, new Position(startX+120, startY+300));
        new WoodenBoots(gc, new Position(startX+150, startY+300));
        new WoodenShield(gc, new Position(startX+180, startY+300));
        new WoodenSword(gc, new Position(startX+210, startY+300));
        new LongWoodenSword(gc, new Position(startX+230, startY+300));

        new IronHelmet(gc, new Position(startX+50, startY+350));
        new IronChestplate(gc, new Position(startX+80, startY+350));
        new IronPants(gc, new Position(startX+120, startY+350));
        new IronBoots(gc, new Position(startX+150, startY+350));
        new IronShield(gc, new Position(startX+180, startY+350));
        new IronSword(gc, new Position(startX+210, startY+350));
        new LongIronSword(gc, new Position(startX+230, startY+350));

        new DiamondHelmet(gc, new Position(startX+50, startY+400));
        new DiamondChestplate(gc, new Position(startX+80, startY+400));
        new DiamondPants(gc, new Position(startX+120, startY+400));
        new DiamondBoots(gc, new Position(startX+150, startY+400));
        new DiamondShield(gc, new Position(startX+180, startY+400));
        new DiamondSword(gc, new Position(startX+210, startY+400));
        new LongDiamondSword(gc, new Position(startX+230, startY+400));

        new RubyHelmet(gc, new Position(startX+50, startY+450));
        new RubyChestplate(gc, new Position(startX+80, startY+450));
        new RubyPants(gc, new Position(startX+120, startY+450));
        new RubyBoots(gc, new Position(startX+150, startY+450));
        new RubyShield(gc, new Position(startX+180, startY+450));
        new RubySword(gc, new Position(startX+210, startY+450));
        new LongRubySword(gc, new Position(startX+230, startY+450));




        WoodenPickaxe pickaxe = new WoodenPickaxe(gc, new Position(startX-150, startY));
        WoodenAxe axe = new WoodenAxe(gc, new Position(startX-20, startY));

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
