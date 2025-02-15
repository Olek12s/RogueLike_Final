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
        double playerDodgeChance = 0.25;
        int wonByEntityA = 0;
        int wonByEntityB = 0;
        int tie = 0;
        for (int i = 0; i < iterations; i++)
        {
            while (entityA.isAlive() && entityB.isAlive())
            {
                //  entityA.entityUpdater.updateChunkAssociation();
                //  entityB.entityUpdater.updateChunkAssociation();
                entityA.entityUpdater.updateRegeneration();
                entityB.entityUpdater.updateRegeneration();

                if (entityB.getName().equals("Player"))
                {
                    if (Math.random() > playerDodgeChance) entityA.updateAttackHitbox(entityB);
                }
                else
                {
                    entityA.updateAttackHitbox(entityB);
                }
                if (entityA.getName().equals("Player"))
                {
                    if (Math.random() > playerDodgeChance) entityB.updateAttackHitbox(entityA);
                }
                else
                {
                    entityB.updateAttackHitbox(entityA);
                }
                if (entityB.getCurrentHealth() <= 0) entityB.setAlive(false);
                if (entityA.getCurrentHealth() <= 0) entityA.setAlive(false);
            }
            if (entityA.isAlive() && !entityB.isAlive()) wonByEntityA++;
            if (entityB.isAlive() && !entityA.isAlive()) wonByEntityB++;
            if (!entityB.isAlive() && !entityA.isAlive()) tie++;

            //randomize stats, heal-up
            entityA.setupStatistics();
            entityB.setupStatistics();
            entityA.setAlive(true);
            entityB.setAlive(true);
            entityA.statistics.setHitPoints(entityA.getMaxHitPoints());
            entityB.statistics.setHitPoints(entityB.getMaxHitPoints());
        }
        System.out.println("Iterations: " + iterations);
        System.out.println(entityA.getName() + " winrate: " + String.format("%.4f", ((double) wonByEntityA / iterations) * 100) + "%" + " Won: " + wonByEntityA);
        System.out.println(entityB.getName() + " winrate: " + String.format("%.4f", ((double) wonByEntityB / iterations) * 100) + "%" + " Won: " + wonByEntityB);
        double tiePercent = ((double) tie / iterations) * 100;
        System.out.println("tie: " + tiePercent + "%");
        //System.out.println("Tie: " + tie);
    }
}