package main.entity;

public enum BehaviourState
{
    WANDER(0),
    FOLLOW_PATH(1);

    private final int id;

    BehaviourState(int id) {this.id = id;}

    public int getId() {return id;}

    public static BehaviourState fromId(int id)
    {
        for (BehaviourState behaviourState : values())
        {
            if (behaviourState.getId() == id)
            {
                return behaviourState;
            }
        }
        throw new IllegalArgumentException("Illegal ID");
    }
}
