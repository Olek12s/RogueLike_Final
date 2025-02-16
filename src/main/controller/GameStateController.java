package main.controller;

public class GameStateController implements Updatable
{
    public GameController gc;
    private GameState currentGameState;
    private boolean inventoryKeyReleased = true;

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
}
