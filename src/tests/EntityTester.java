package tests;

import main.controller.GameController;
import main.entity.monster.BitingSlime;
import test.annotations.Before;
import test.annotations.Test;
import test.runner.Assertions;
import utilities.Position;

public class EntityTester
{
    BitingSlime entityA;
    BitingSlime entityB;
    GameController gc;

  @Before
  public EntityTester()
  {
      this.gc = new GameController();
      this.entityA = new BitingSlime(gc, new Position(0,0));
      this.entityB = new BitingSlime(gc, new Position(0,0));
  }

    @Test
    public void isAlive()
    {
        Assertions.assertTrue(entityA.isAlive());
        entityA.setAlive(false);
        Assertions.assertFalse(entityA.isAlive());
    }

    @Test
    public void checkHealth()
    {
        entityA.statistics.setHitPoints(50);
        Assertions.assertEquals(entityA.getCurrentHealth(), 50);
    }

    @Test
    public void checkRegeneration()
    {
        entityA.statistics.setMaxHitPoints(50);
        entityA.statistics.setHitPoints(49);
        for (int i = 0; i < 500; i++) entityA.entityUpdater.updateRegeneration();
        Assertions.assertEquals(entityA.getCurrentHealth(), 50);
    }

    @Test
    public void checkAttack()
    {
        entityB.statistics.setHitPoints(50);
        entityA.statistics.setStrength(35, 0, 0);
        for (int i = 0; i < 50; i++) entityA.updateAttackHitbox(entityB);
        Assertions.assertTrue(entityB.getCurrentHealth() != 50);
    }

}
