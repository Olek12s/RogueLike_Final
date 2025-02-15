package main.entity.player;

import main.Direction;
import main.controller.Updatable;
import main.entity.Entity;
import main.entity.EntityUpdater;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.item.Consumable;
import main.item.Item;
import utilities.Hitbox;
import utilities.KeyHandler;
import utilities.Position;
import world.generation.CaveNegativeOneGenerator;
import world.generation.CaveNegativeTwoGenerator;
import world.map.Map;
import world.map.MapController;
import world.map.MapLevels;
import world.map.tiles.Tile;
import world.map.tiles.TileID;

public class PlayerUpdater extends EntityUpdater implements Updatable
{
    Player entity;
    private boolean beltKeyProcessed = false;
    private boolean attackKeyProcessed = false;

    public PlayerUpdater(Entity entity)
    {
        super(entity);
        this.entity = (Player)entity;
    }

    int counter = 0;
    @Override
    public void update()
    {
        super.update();
        updatePlayerDirection();
        checkEnteranceCollision();
        checkPickUpItem();
        checkCrouch();
        processClickedSlotIndex();
        attackIfClicked();
        updateMeleeAttack(null);
        counter++;
        if (counter == 60)  // DEBUG
        {
            counter = 0;
            //System.out.println(entity.getWorldPosition().x/Tile.tileSize + " " + entity.getWorldPosition().y/Tile.tileSize);
            //System.out.println("Level: " + entity.gc.mapController.getCurrentMap().getLevel());
        }
    }

    private void checkPickUpItem()
    {
       // if (entity.gc.keyHandler.F_PRESSED && entity.gc.gameStateController.getCurrentGameState() == GameState.PENDING) entity.pickUpItem();
        if (entity.gc.keyHandler.F_PRESSED) entity.pickUpItem();
    }

    private void checkCrouch()
    {
        if (entity.gc.keyHandler.CTRL_PRESSED)
        {
            entity.setCrouching(true);
        }
        else entity.setCrouching(false);
    }

    private void updatePlayerDirection()
    {
        entity.isMoving = false;
        if (entity.gc.keyHandler.W_PRESSED && entity.gc.keyHandler.A_PRESSED || entity.gc.keyHandler.UP_PRESSED && entity.gc.keyHandler.LEFT_PRESSED)     // Direction Up-Left
        {
            entity.setDirection(Direction.UP_LEFT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.W_PRESSED && entity.gc.keyHandler.D_PRESSED || entity.gc.keyHandler.UP_PRESSED && entity.gc.keyHandler.RIGHT_PRESSED)     // Direction Up-Right
        {
            entity.setDirection(Direction.UP_RIGHT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.S_PRESSED && entity.gc.keyHandler.A_PRESSED || entity.gc.keyHandler.DOWN_PRESSED && entity.gc.keyHandler.LEFT_PRESSED)     // Direction Down-Left
        {
            entity.setDirection(Direction.DOWN_LEFT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.S_PRESSED && entity.gc.keyHandler.D_PRESSED || entity.gc.keyHandler.DOWN_PRESSED && entity.gc.keyHandler.RIGHT_PRESSED)     // Direction Down-Right
        {
            entity.setDirection(Direction.DOWN_RIGHT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.S_PRESSED || entity.gc.keyHandler.DOWN_PRESSED)   // Direction Down
        {
            entity.setDirection(Direction.DOWN);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.A_PRESSED || entity.gc.keyHandler.LEFT_PRESSED)  // Direction Left
        {
            entity.setDirection(Direction.LEFT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.D_PRESSED || entity.gc.keyHandler.RIGHT_PRESSED) // Direction right
        {
            entity.setDirection(Direction.RIGHT);
            entity.isMoving = true;
        }
        else if (entity.gc.keyHandler.W_PRESSED || entity.gc.keyHandler.UP_PRESSED)   // Direction up
        {
            entity.setDirection(Direction.UP);
            entity.isMoving = true;
        }
    }

    private void checkEnteranceCollision()
    {
        Map currentMap = MapController.getCurrentMap();
        Position currentPosition = entity.getHitbox().getCenterWorldPosition();
        Tile currentTile = currentMap.getTile(currentPosition);

        if (entity.isCollidingWithEnterance && !currentTile.isCavePassage())
        {
            entity.isCollidingWithEnterance = false;
        }

        if (!entity.isCollidingWithEnterance)
        {
            int tileId = currentMap.getTile(currentPosition).getId();
            TileID tileID = TileID.fromId(tileId);

            switch (tileID)
            {
                case CAVE_ENTRANCE:

                    if (MapController.getMapByLevel(MapLevels.CAVE_NEGATIVE_ONE) == null)
                    {
                        CaveNegativeOneGenerator.createCaveNegativeOneMap(1024, 1024);
                    }

                    entity.gc.mapController.changeMapForPlayer((Player)entity, MapLevels.CAVE_NEGATIVE_ONE);
                    break;
                case CAVE_DEEP_ENTRANCE:
                    if (MapController.getMapByLevel(MapLevels.CAVE_NEGATIVE_TWO) == null)
                    {
                        CaveNegativeTwoGenerator.createCaveNegativeTwoMap(1024, 1024);
                    }
                    entity.gc.mapController.changeMapForPlayer((Player)entity, MapLevels.CAVE_NEGATIVE_TWO);

                    break;
                case CAVE_RUINS_ENTRANCE:
                    if (MapController.getMapByLevel(MapLevels.CAVE_RUINS) == null)
                    {
                        //generator
                        entity.gc.mapController.changeMapForPlayer((Player) entity, MapLevels.CAVE_RUINS);
                    }
                    break;

                case CAVE_EXIT:
                    entity.gc.mapController.changeMapForPlayer((Player)entity, MapLevels.SURFACE);
                    break;
                case CAVE_DEEP_EXIT:
                    entity.gc.mapController.changeMapForPlayer((Player)entity, MapLevels.CAVE_NEGATIVE_ONE);
                    break;
                case CAVE_RUINS_EXIT:
                    entity.gc.mapController.changeMapForPlayer((Player)entity, MapLevels.CAVE_NEGATIVE_TWO);
                    break;
            }
            if (currentTile.isCavePassage())
            {
                entity.isCollidingWithEnterance = true;
            }
        }
    }

    /**
     * Updates currently selected slot from belt on-click. By default currently selected slot index is 0.
     * If clicked slot can be processed, index of selected item won't change, but action of implemented interface
     * will be executed first.
     * Clicked slot is processed only once on button hold.
     */
    private void processClickedSlotIndex()
    {
        KeyHandler kh = entity.gc.keyHandler;
        int slotCount = Inventory.INVENTORY_BELT_SLOTS;
        int clickedIDX = -1;

        boolean[] numberKeys = {
                kh.ONE_PRESSED, kh.TWO_PRESSED, kh.THREE_PRESSED,
                kh.FOUR_PRESSED, kh.FIVE_PRESSED, kh.SIX_PRESSED,
                kh.SEVEN_PRESSED, kh.EIGHT_PRESSED, kh.NINE_PRESSED
        };
        for (int i = 0; i < numberKeys.length && i < slotCount; i++)
        {
            if (numberKeys[i])
            {
                clickedIDX = i;
                break;
            }
        }
        if (clickedIDX == -1)
        {
            beltKeyProcessed = false;
            return;
        }
        if (beltKeyProcessed) return;

        // CONSUMABLE LOGIC //
        Slot clickedSlot = entity.getInventory().getBeltSlots()[clickedIDX];
        if (clickedSlot.getStoredItem() instanceof Consumable)
        {
            ((Consumable) clickedSlot.getStoredItem()).consume(entity);
            clickedSlot.setStoredItem(null);
        }

        // CHANGING SLOT INDEX //
        else
        {
            entity.setCurrentBeltSlotIndex(clickedIDX);
        }
        beltKeyProcessed = true;
    }

    private void attackIfClicked()
    {
        KeyHandler kh = entity.gc.keyHandler;
        Item currentWeapon = entity.getCurrentBeltSlot().getStoredItem();
        if (currentWeapon == null) currentWeapon = entity.getBareHands();
        int attackWidth = currentWeapon.getMeleeAttackWidth();
        int attackHeight = currentWeapon.getMeleeAttackHeight();
        System.out.println("W " + attackWidth);
        entity.setAttackHitbox(new Hitbox(entity.getHitbox().getCenterWorldPosition(), attackWidth, attackHeight));

        if (kh.SPACE_PRESSED && !attackKeyProcessed)
        {
            System.out.println("attack");
            attackKeyProcessed = true;
        }
        if (!kh.SPACE_PRESSED)
        {
            attackKeyProcessed = false;
            entity.setAttackHitbox(null);
        }
    }
}
