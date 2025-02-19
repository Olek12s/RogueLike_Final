package main.controller;

public class GameStateController implements Updatable
{
    public GameController gc;
    private GameState currentGameState;
    private boolean inventoryKeyReleased = true;
    private boolean craftingKeyReleased = true;

    public GameState getCurrentGameState() {return currentGameState;}
    public void setCurrentGameState(GameState currentGameState) {this.currentGameState = currentGameState;}

    public GameStateController(GameController gc)
    {
        this.gc = gc;
        currentGameState = GameState.PENDING;
        gc.updatables.add(this);
    }

    @Override
    public void update()
    {
        checkInventoryState();
        checkCraftingState();
    }

    private void checkInventoryState()
    {
        if (!gc.keyHandler.I_PRESSED && !gc.keyHandler.E_PRESSED)
        {
            inventoryKeyReleased = true;
        }

        if (inventoryKeyReleased && (gc.keyHandler.I_PRESSED || gc.keyHandler.E_PRESSED))
        {
            if (currentGameState == GameState.PENDING) {
                currentGameState = GameState.INVENTORY;
            }
            else if (currentGameState == GameState.INVENTORY)
            {
                currentGameState = GameState.PENDING;
            }
            inventoryKeyReleased = false;
        }
    }

    private void checkCraftingState()
    {
        if (!gc.keyHandler.C_PRESSED)
        {
            craftingKeyReleased = true;
        }

        if (craftingKeyReleased && gc.keyHandler.C_PRESSED)
        {
            if (currentGameState == GameState.PENDING)
            {
                currentGameState = GameState.CRAFTING;
            }
            else if (currentGameState == GameState.CRAFTING)
            {
                currentGameState = GameState.PENDING;
            }
            craftingKeyReleased = false;
        }
    }
}
