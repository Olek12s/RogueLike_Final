package main.entity.player;

import main.Direction;
import main.GameController;
import main.entity.Entity;
import main.entity.EntityRenderer;
import main.entity.EntityUpdater;
import main.item.BitingSlimeWeapon;
import main.item.ZombieWeapon;
import utilities.*;

public class Player extends Entity
{
    private static Position startingPosition = new Position(-5632+500,-5632+680);
    BitingSlimeWeapon weapon;


    public Player(GameController gc)
    {
        super(gc, startingPosition, 0); // player's entityID is 0!
        setMovementSpeed(70);
        setupStatistics();
    }

    public Player(GameController gc, Position position)
    {
        super(gc, position, 0);
        this.worldPosition = worldPosition;
        this.weapon = new BitingSlimeWeapon();
        this.isImmobilised = true;
        this.name = "Player";
        setupStatistics();
        setAlive(true);
    }

    @Override
    public PlayerRenderer setRenderer()
    {
        SpriteSheet spriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/default/bitingSlime22"), 22);
        return new PlayerRenderer(this, spriteSheet);
    }

    @Override
    public PlayerUpdater setUpdater()
    {
        return new PlayerUpdater(this);
    }

    @Override
    public void attack(Entity target) {

    }

    @Override
    public void setupStatistics()
    {
        statistics.maxHitPoints = 100;
        statistics.setArmour(0);
        statistics.setRegeneration(1);
        statistics.hitPoints = statistics.maxHitPoints;
    }

    @Override
    public void setDefaultSprite()
    {
        //currentSprite = renderer.getSpriteSheet().extractFirst();
        currentSprite = PlayerRenderer.getSpriteSheetByID(entityID).extractFirst();
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
}
