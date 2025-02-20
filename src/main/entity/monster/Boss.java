package main.entity.monster;

import main.Direction;
import main.controller.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.ingredients.Diamond;
import main.item.ingredients.Ruby;
import main.item.weapon.mobweapon.BossWeapon;
import main.item.weapon.mobweapon.ZombieWeapon;
import utilities.FileManipulation;
import utilities.Hitbox;
import utilities.Position;
import utilities.sprite.SpriteSheet;
import world.map.tiles.Tile;

public class Boss extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/entity/bitingSlimeGreen22Boss"), 22);
    public int level = 6;


    public Boss(GameController gc, Position worldPosition)
    {
        super(gc, EntityID.Boss.ID, worldPosition);
        this.getInventory().getBeltSlots()[0].setStoredItem(new BossWeapon(gc));
        this.isImmobilised = false;
        this.name = "Boss";
        setupStatistics();
        setAlive(true);
        for (int i = 0; i < 12; i++) giveItemAtChance(new Diamond(gc), 0.8f);
        for (int i = 0; i < 12; i++) giveItemAtChance(new Ruby(gc), 0.8f);
    }

    public void setupStatistics()
    {
        this.statistics.setStrength(80, 0, 0);
        this.statistics.setBaseArmour(5);
        this.statistics.setHitPoints(2000, 0, 0);
        this.statistics.setRegeneration(10);
        this.statistics.setMaxMovementSpeed(3);
        this.statistics.setExpReward(99999);
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
