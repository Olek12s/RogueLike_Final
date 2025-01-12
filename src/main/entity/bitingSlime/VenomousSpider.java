package main.entity.bitingSlime;

import main.DamageType;
import main.Direction;
import main.GameController;
import main.entity.Entity;
import main.entity.EntityID;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.SpiderWeapon;
import utilities.FileManipulation;
import utilities.Hitbox;
import utilities.Position;
import utilities.sprite.SpriteSheet;

// entity class level 4
public class VenomousSpider extends Entity
{
    static SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/defaultEntity"), 26);
    SpiderWeapon weapon;
    private int attackPreparationCounter = 0;
    private int attackRestCounter = 0;
    public int level = 4;


    public VenomousSpider(GameController gc, Position worldPosition)
    {
        super(gc, EntityID.VenomousSpider.ID, worldPosition);
        this.weapon = new SpiderWeapon();
        this.isImmobilised = false;
        this.name = "Venomous Spider";
        setupStatistics();
        setAlive(true);
    }

    public void setupStatistics()  // add poison effect
    {
        this.statistics.setStrength(6, 3, 2);
        this.statistics.setArmour(3);
        this.statistics.setHitPoints(30, 7, 7);
        this.statistics.setRegeneration(2);
        this.statistics.setMovementSpeed(30);
    }

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


    /**
     * Executes the primary and only type of attack for this entity, when hitting target is possible
     *
     *
     * @param target The entity that will be attacked by this entity
     */
    public void attack(Entity target)
    {
        if (isAlive() == false) return; // can't attack if dead
        if (attackRestCounter < weapon.getAttackRestTime())
        {
            attackRestCounter++;
            return; // can't attack when resting
        }

        // if target is within range, start charging attack
        if (attackPreparationCounter == 0 && this.distanceBetween(target) <= this.weapon.getMeleeAttackHeight())
        {
            attackPreparationCounter++;
        }

        // continue charging attack even if target is out of range
        if (attackPreparationCounter > 0)
        {
            if (attackPreparationCounter < this.weapon.getAttackPreparationTime())
            {
                attackPreparationCounter++;
            }
            else // if attack is charged - attack (add animation)
            {
                if (this.distanceBetween(target) <= this.weapon.getMeleeAttackHeight()) // if target is within range (change it to the hitbox which will damage first target?)
                {
                    target.receiveDamage(calculateDamageOutput(weapon), DamageType.PHYSICAL);
                }

                attackPreparationCounter = 0;
                attackRestCounter = 0;
            }
        }
    }
}
