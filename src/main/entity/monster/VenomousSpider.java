package main.entity.monster;

import main.Direction;
import main.controller.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.mobweapon.SpiderWeapon;
import utilities.FileManipulation;
import utilities.Hitbox;
import utilities.Position;
import utilities.sprite.SpriteSheet;
import world.map.tiles.Tile;

// entity class level 4
public class VenomousSpider extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/defaultEntity"), 26);
    public int level = 4;


    public VenomousSpider(GameController gc, Position worldPosition)
    {
        super(gc, EntityID.VenomousSpider.ID, worldPosition);
        this.getInventory().getBeltSlots()[0].setStoredItem(new SpiderWeapon(gc, worldPosition));
        this.isImmobilised = false;
        this.name = "Venomous Spider";
        setupStatistics();
        setAlive(true);
    }

    public void setupStatistics()  // add poison effect
    {
        this.statistics.setStrength(6, 3, 2);
        this.statistics.setBaseArmour(3);
        this.statistics.setHitPoints(30, 7, 7);
        this.statistics.setRegeneration(2);
        this.statistics.setMaxMovementSpeed(1);
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
