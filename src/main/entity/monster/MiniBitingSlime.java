package main.entity.monster;

import main.Direction;
import main.controller.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.mobweapon.BitingSlimeWeapon;
import utilities.FileManipulation;
import utilities.Hitbox;
import utilities.Position;
import utilities.sprite.SpriteSheet;
import world.map.tiles.Tile;


// entity class level 1
public class MiniBitingSlime extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22);
    public int level = 1;


    public MiniBitingSlime(GameController gc, Position worldPosition)
    {
        super(gc, EntityID.MiniBitingSlime.ID, worldPosition);
        this.getInventory().getBeltSlots()[0].setStoredItem(new BitingSlimeWeapon(gc, worldPosition));
        this.isImmobilised = false;
        this.name = "Small slime";
        setupStatistics();
        setAlive(true);
    }

    public void setupStatistics()
    {
        this.statistics.setStrength(6, 0, 2); // 6
        this.statistics.setBaseArmour(0);
        this.statistics.setHitPoints(16, 3,3);
        this.statistics.setRegeneration(1);
        this.statistics.setMaxMovementSpeed(5);
    }

    @Override
    public void setDetectionRadius() {
        setDetectionDiameter(100 * Tile.tileSize);}

    @Override
    public void setLoseInterestRadius() {
        setLoseInterestDiameter(110 * Tile.tileSize);}

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
