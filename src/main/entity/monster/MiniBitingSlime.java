package main.entity.monster;

import main.Direction;
import main.controller.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.ingredients.Coal;
import main.item.ingredients.Diamond;
import main.item.ingredients.Slime;
import main.item.ingredients.Wood;
import main.item.weapon.mobweapon.BitingSlimeWeapon;
import utilities.FileManipulation;
import utilities.Hitbox;
import utilities.Position;
import utilities.sprite.SpriteSheet;
import world.map.tiles.Tile;


// entity class level 1
public class MiniBitingSlime extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/entity/bitingSlimeGreen22"), 22);
    public int level = 1;


    public MiniBitingSlime(GameController gc, Position worldPosition)
    {
        super(gc, EntityID.MiniBitingSlime.ID, worldPosition);
        this.getInventory().getBeltSlots()[0].setStoredItem(new BitingSlimeWeapon(gc));
        this.isImmobilised = false;
        this.name = "Small slime";
        setupStatistics();
        setAlive(true);
        giveItemAtChance(new Slime(gc), 0.5f);
        giveItemAtChance(new Wood(gc), 0.35f);
        giveItemAtChance(new Coal(gc), 0.1f);
    }

    public void setupStatistics()
    {
        this.statistics.setStrength(2, 0, 2);
        this.statistics.setBaseArmour(0);
        this.statistics.setHitPoints(16, 3,3);
        this.statistics.setRegeneration(1);
        this.statistics.setMaxMovementSpeed(5);
        this.statistics.setExpReward(5);
    }

    @Override
    public void setDetectionRadius() {
        setDetectionDiameter(14 * Tile.tileSize);}

    @Override
    public void setLoseInterestRadius() {
        setLoseInterestDiameter(28 * Tile.tileSize);}

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
