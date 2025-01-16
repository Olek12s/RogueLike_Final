package tests.entityBalanceTester;

import main.controller.GameController;
import main.entity.Entity;
import main.entity.monster.*;
import utilities.Position;

public class Main
{
    static GameController gc = new GameController();
    static Position position = new Position(0, 0);
    static Entity miniSlimeA = new MiniBitingSlime(gc, position);
    static Entity miniSlimeB = new MiniBitingSlime(gc, position);
    static Entity slimeA = new BitingSlime(gc, position);
    static Entity slimeB = new BitingSlime(gc, position);
    static Entity spiderA = new Spider(gc, position);
    static Entity spiderB = new Spider(gc, position);
    static Entity venomousSpiderA = new VenomousSpider(gc, position);
    static Entity venomousSpiderB = new VenomousSpider(gc, position);
    static Entity zombieA = new Zombie(gc, position);
    static Entity zombieB = new Zombie(gc, position);
    static Entity player = new PlayerEntity(gc, position);

    public static void main(String[] args)
    {
        BattleSimulation bs = new BattleSimulation(miniSlimeA, venomousSpiderB);

        bs.simulateBattle(100000);
    }
}
