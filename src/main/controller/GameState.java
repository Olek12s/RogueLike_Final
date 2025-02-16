package main.controller;

public enum GameState
{
    PENDING(0),
    INVENTORY(1),
    PAUSED(10),
    GAME_OVER(100);

    private final int id;

    GameState(int id) {this.id = id;}

    public int getId() {return id;}

    public static GameState fromId(int id)
    {
        for (GameState gameState : values())
        {
            if (gameState.getId() == id)
            {
                return gameState;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
