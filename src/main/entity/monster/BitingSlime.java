package main.entity.monster;

import main.Direction;
import main.controller.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.ingredients.Coal;
import main.item.ingredients.IronOre;
import main.item.ingredients.Slime;
import main.item.ingredients.Wood;
import main.item.weapon.mobweapon.BitingSlimeWeapon;
import utilities.FileManipulation;
import utilities.Hitbox;
import utilities.Position;
import utilities.sprite.SpriteSheet;
import world.map.tiles.Tile;

// entity class level 2
public class BitingSlime extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/entity/bitingSlimeGreen22Brown"), 22);
    public int level = 2;


    public BitingSlime(GameController gc, Position worldPosition)
    {
        super(gc, EntityID.BitingSlime.ID, worldPosition);
        this.getInventory().getBeltSlots()[0].setStoredItem(new BitingSlimeWeapon(gc));
        this.isImmobilised = false;
        this.name = "Slime";
        setupStatistics();
        setAlive(true);
        giveItemAtChance(new Slime(gc), 0.5f);
        giveItemAtChance(new Wood(gc), 0.5f);
        giveItemAtChance(new Coal(gc), 0.35f);
        giveItemAtChance(new IronOre(gc), 0.1f);
    }

    public void setupStatistics()
    {
        this.statistics.setStrength(4, 1, 2);
        this.statistics.setBaseArmour(0);
        this.statistics.setHitPoints(23, 4,3);
        this.statistics.setRegeneration(1);
        this.statistics.setMaxMovementSpeed(1);
        this.statistics.setExpReward(20);
    }

    @Override
    public void setDetectionRadius() {
        setDetectionDiameter(8 * Tile.tileSize);}

    @Override
    public void setLoseInterestRadius() {
        setLoseInterestDiameter(16 * Tile.tileSize);}

    @Override
    public void setDefaultSprite()
    {
        currentSprite = EntityRenderer.getSpriteSheetByID(entityID).extractFirst();
    }

    @Override
    public void setHitbox()
    {
        hitbox = new Hitbox(worldPosition, (int)(currentSprite.resolutionX), (int)(currentSprite.resolutionY));
    }

    @Override
    public void setDirection()
    {
        direction = Direction.DOWN;
    }

    @Override
    public void setWorldPosition(Position worldPosition)
    {
        this.worldPosition = worldPosition;
    }

    @Override
    public EntityRenderer setRenderer()
    {
        return new EntityRenderer(this, spriteSheet);
    }

    @Override
    public EntityUpdater setUpdater()
    {
        return new EntityUpdater(this);
    }
}
