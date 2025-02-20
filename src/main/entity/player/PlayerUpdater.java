package main.entity.player;

import main.Direction;
import main.controller.GameController;
import main.controller.GameState;
import main.controller.Updatable;
import main.entity.Entity;
import main.entity.EntityUpdater;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.item.Consumable;
import main.item.Item;
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
    Player playerEntity;
    private boolean beltKeyProcessed = false;
    private boolean attackKeyProcessed = false;

    public PlayerUpdater(Entity entity)
    {
        super(entity);
        this.playerEntity = (Player)entity;
    }

    int counter = 0;
    @Override
    public void update()
    {
        super.update();
        checkPickUpItem();
        if (playerEntity.isAlive())
        {
            updatePlayerDirection();
            checkEnteranceCollision();
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
        if (!playerEntity.isAlive())    // called only once, when player changes state to not alive
        {
            playerEntity.gc.gameStateController.setCurrentGameState(GameState.GAME_OVER);
        }
    }

    private void checkPickUpItem()
    {
       // if (entity.gc.keyHandler.F_PRESSED && entity.gc.gameStateController.getCurrentGameState() == GameState.PENDING) entity.pickUpItem();
        if (playerEntity.gc.keyHandler.F_PRESSED)
        {
            playerEntity.pickUpItem();
        }
    }

    private void checkCrouch()
    {
        if (playerEntity.gc.keyHandler.CTRL_PRESSED)
        {
            playerEntity.setCrouching(true);
        }
        else playerEntity.setCrouching(false);
    }

    private void updatePlayerDirection()
    {
        playerEntity.setMoving(false);
        if (playerEntity.gc.keyHandler.W_PRESSED && playerEntity.gc.keyHandler.A_PRESSED || playerEntity.gc.keyHandler.UP_PRESSED && playerEntity.gc.keyHandler.LEFT_PRESSED)     // Direction Up-Left
        {
            playerEntity.setDirection(Direction.UP_LEFT);
            playerEntity.setMoving(true);
        }
        else if (playerEntity.gc.keyHandler.W_PRESSED && playerEntity.gc.keyHandler.D_PRESSED || playerEntity.gc.keyHandler.UP_PRESSED && playerEntity.gc.keyHandler.RIGHT_PRESSED)     // Direction Up-Right
        {
            playerEntity.setDirection(Direction.UP_RIGHT);
            playerEntity.setMoving(true);
        }
        else if (playerEntity.gc.keyHandler.S_PRESSED && playerEntity.gc.keyHandler.A_PRESSED || playerEntity.gc.keyHandler.DOWN_PRESSED && playerEntity.gc.keyHandler.LEFT_PRESSED)     // Direction Down-Left
        {
            playerEntity.setDirection(Direction.DOWN_LEFT);
            playerEntity.setMoving(true);
        }
        else if (playerEntity.gc.keyHandler.S_PRESSED && playerEntity.gc.keyHandler.D_PRESSED || playerEntity.gc.keyHandler.DOWN_PRESSED && playerEntity.gc.keyHandler.RIGHT_PRESSED)     // Direction Down-Right
        {
            playerEntity.setDirection(Direction.DOWN_RIGHT);
            playerEntity.setMoving(true);
        }
        else if (playerEntity.gc.keyHandler.S_PRESSED || playerEntity.gc.keyHandler.DOWN_PRESSED)   // Direction Down
        {
            playerEntity.setDirection(Direction.DOWN);
            playerEntity.setMoving(true);
        }
        else if (playerEntity.gc.keyHandler.A_PRESSED || playerEntity.gc.keyHandler.LEFT_PRESSED)  // Direction Left
        {
            playerEntity.setDirection(Direction.LEFT);
            playerEntity.setMoving(true);
        }
        else if (playerEntity.gc.keyHandler.D_PRESSED || playerEntity.gc.keyHandler.RIGHT_PRESSED) // Direction right
        {
            playerEntity.setDirection(Direction.RIGHT);
            playerEntity.setMoving(true);
        }
        else if (playerEntity.gc.keyHandler.W_PRESSED || playerEntity.gc.keyHandler.UP_PRESSED)   // Direction up
        {
            playerEntity.setDirection(Direction.UP);
            playerEntity.setMoving(true);
        }
    }

    private void checkEnteranceCollision()
    {
        Map currentMap = MapController.getCurrentMap();
        Position currentPosition = playerEntity.getHitbox().getCenterWorldPosition();
        Tile currentTile = currentMap.getTile(currentPosition);

        if (playerEntity.isCollidingWithEnterance && !currentTile.isCavePassage())
        {
            playerEntity.isCollidingWithEnterance = false;
        }

        if (!playerEntity.isCollidingWithEnterance)
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

                    playerEntity.gc.mapController.changeMapForPlayer((Player) playerEntity, MapLevels.CAVE_NEGATIVE_ONE);
                    break;
                case CAVE_DEEP_ENTRANCE:
                    if (MapController.getMapByLevel(MapLevels.CAVE_NEGATIVE_TWO) == null)
                    {
                        CaveNegativeTwoGenerator.createCaveNegativeTwoMap(1024, 1024);
                    }
                    playerEntity.gc.mapController.changeMapForPlayer((Player) playerEntity, MapLevels.CAVE_NEGATIVE_TWO);

                    break;
                case CAVE_RUINS_ENTRANCE:
                    if (MapController.getMapByLevel(MapLevels.CAVE_RUINS) == null)
                    {
                        //generator
                        playerEntity.gc.mapController.changeMapForPlayer((Player) playerEntity, MapLevels.CAVE_RUINS);
                    }
                    break;

                case CAVE_EXIT:
                    playerEntity.gc.mapController.changeMapForPlayer((Player) playerEntity, MapLevels.SURFACE);
                    break;
                case CAVE_DEEP_EXIT:
                    playerEntity.gc.mapController.changeMapForPlayer((Player) playerEntity, MapLevels.CAVE_NEGATIVE_ONE);
                    break;
                case CAVE_RUINS_EXIT:
                    playerEntity.gc.mapController.changeMapForPlayer((Player) playerEntity, MapLevels.CAVE_NEGATIVE_TWO);
                    break;
            }
            if (currentTile.isCavePassage())
            {
                playerEntity.isCollidingWithEnterance = true;
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
        KeyHandler kh = playerEntity.gc.keyHandler;
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
        Slot clickedSlot = playerEntity.getInventory().getBeltSlots()[clickedIDX];
        if (clickedSlot.getStoredItem() instanceof Consumable)
        {
            ((Consumable) clickedSlot.getStoredItem()).consume(playerEntity);
            clickedSlot.setStoredItem(null);
        }

        // CHANGING SLOT INDEX //
        else
        {
            playerEntity.setCurrentBeltSlotIndex(clickedIDX);
        }
        beltKeyProcessed = true;
    }

    private void attackIfClicked()
    {
        KeyHandler kh = playerEntity.gc.keyHandler;
        Item currentWeapon = playerEntity.getCurrentBeltSlot().getStoredItem();
        if (currentWeapon == null) currentWeapon = playerEntity.getBareHands();
        int attackWidth = currentWeapon.getMeleeAttackWidth();
        int attackHeight = currentWeapon.getMeleeAttackHeight();

        if (kh.SPACE_PRESSED && !attackKeyProcessed)
        {
            attackKeyProcessed = true;
            //updateMeleeAttack(null);
            playerEntity.setDuringMeleeAttack(true);
        }
        if (!kh.SPACE_PRESSED)
        {
            attackKeyProcessed = false;
        }
    }
}
