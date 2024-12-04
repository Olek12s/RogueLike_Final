package tests.entityBalanceTester;

import main.entity.Entity;
import utilities.Position;

public class BattleSimulation
{
    private Entity entityA;
    private Entity entityB;

    public BattleSimulation(Entity entityA, Entity entityB)
    {
        this.entityA = entityA;
        this.entityB = entityB;
        this.entityA.setWorldPosition(new Position(0,0));
        this.entityB.setWorldPosition(new Position(0,0));
    }

    public void simulateBattle(int iterations)
    {
        int wonByEntityA = 0;
        int wonByEntityB = 0;
        for (int i = 0; i < iterations; i++)
        {
            while (entityA.isAlive() || entityB.isAlive())
            {
                entityA.attack(entityB);
                entityB.attack(entityA);
                entityA.entityUpdater.updateAttack();
            }
            //randomize stats, heal-up
            entityA.setupStatistics();
            entityB.setupStatistics();
            entityA.setAlive(true);
            entityB.setAlive(true);
            entityA.statistics.setHitPoints(entityA.getMaxHitPoints());
            entityB.statistics.setHitPoints(entityB.getMaxHitPoints());
        }
    }
}
