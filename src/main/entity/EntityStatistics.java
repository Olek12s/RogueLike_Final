package main.entity;

public class EntityStatistics
{
    Entity entity;

    public static int HP_UPDATE_BONUS = 10;
    public static int MANA_UPDATE_BONUS = 10;
    public static int ARMOUR_UPDATE_BONUS = 1;
    public static int MAGIC_ARMOUR_UPDATE_BONUS = 1;
    public static int STRENGTH_UPDATE_BONUS = 1;
    public static int STAMINA_UPDATE_BONUS = 5;
    public static int REGENERATION_UPDATE_BONUS = 1;
    public static int DEXTERITY_UPDATE_BONUS = 1;
    public static int INTELLECT_UPDATE_BONUS = 1;
    public static float NEXT_LEVEL_EXP_MULTIPLER = 1.618f;

    private int maxHitPoints = 1;                // maximum hit points
    private int hitPoints = maxHitPoints;        // current hit points - if 0 or lower - entity dies
    private int regeneration = 1;             // regeneration of health per second. By default 1

    private int maxMana = 1;                     // current mana - needed to cast spells or hold specific items
    private int mana = maxMana;                  // max mana - how much spells you can cast at short amount of time

    private int maxMovementSpeed;         // how fast entity travels through map
    private int currentMovementSpeed;

    private int baseArmour;
    private int baseMagicArmour;
    private int armour;                       // how resistant entity is against physical damage
    private int magicArmour;                  // how resistant entity is against magical damage

    private int strength;                     // statistic exclusive for melee weapons - how strong you hit
    private int dexterity;                    // statistic exclusive for bows and actions needing dexterity - how powerful you can shoot
    private int intellect;                    // statistic exclusive for magic - how strong your spells are

    private int stamina;                      // statistic used in blocking attacks and fighting melee weapons. Lower stamina = lower damage and weaker ability to block
    private int exp;
    private int expReward;
    private int nextLevelExp;

    public int getMaxHitPoints() {return maxHitPoints;}
    public void setMaxHitPoints(int maxHitPoints) {this.maxHitPoints = maxHitPoints;}
    public int getHitPoints() {return hitPoints;}
    public void setHitPoints(int hitPoints)
    {
        if (hitPoints > maxHitPoints) maxHitPoints = hitPoints;
        this.hitPoints = hitPoints;
    }
    public int getRegeneration() {return regeneration;}
    public void setRegeneration(int regeneration) {this.regeneration = regeneration;}
    public int getMaxMana() {return maxMana;}
    public void setMaxMana(int maxMana) {this.maxMana = maxMana;}
    public int getMana() {return mana;}
    public void setMana(int mana) {this.mana = mana;}
    public int getMaxMovementSpeed() {return maxMovementSpeed;}
    public Entity getEntity() {return entity;}
    public void setEntity(Entity entity) {this.entity = entity;}
    public void setStrength(int strength) {this.strength = strength;}
    public void setExp(int exp) {this.exp = exp;}
    public void setNextLevelExp(int nextLevelExp) {this.nextLevelExp = nextLevelExp;}

    public int getCurrentMovementSpeed() {return currentMovementSpeed;}
    public void setCurrentMovementSpeed(int currentMovementSpeed) {this.currentMovementSpeed = currentMovementSpeed;}

    public int getExp() {return exp;}
    public int getNextLevelExp() {return nextLevelExp;}

    public int getExpReward() {return expReward;}
    public void setExpReward(int expReward) {this.expReward = expReward;}

    public void setMaxMovementSpeed(int speed)
    {
        if (speed == 0) maxMovementSpeed = 0;
        else
        {
            maxMovementSpeed = Math.max(speed, 1);
        }
    }
    public int getArmour() {return armour;}
    public void setArmour(int armour) {this.armour = armour;}
    public int getMagicArmour() {return magicArmour;}
    public void setMagicArmour(int magicArmour) {this.magicArmour = magicArmour;}

    public int getBaseArmour() {return baseArmour;}
    public void setBaseArmour(int baseArmour) {this.baseArmour = baseArmour;}
    public int getBaseMagicArmour() {return baseMagicArmour;}
    public void setBaseMagicArmour(int baseMagicArmour) {this.baseMagicArmour = baseMagicArmour;}

    public int getStrength() {return strength;}

    /**
     * Adds or subtracts from strength value
     * Example: for strength 4 and minChange = 1, maxChange = 3 new Strength will be in range <3, 7>
     * Example: for strength 4 and minChange = 5, maxChange = 5 new Strength will be in range <1, 9>
     * @param strength - base value
     * @param minChange - strength - minChange
     * @Param maxChange - strength + maxChange
     */
    public void setStrength(int strength, int minChange, int maxChange)
    {
        boolean increase = Math.random() > 0.5;
        int randomChange;

        if (increase)
        {
            randomChange = (int) (Math.random() * (maxChange + 1));
        }
        else
        {
            randomChange = (int) (Math.random() * (minChange + 1));
            randomChange = -randomChange;
        }
        int randStrength = strength + randomChange;

        this.strength = Math.max(randStrength, 1);
    }

    public void setHitPoints(int hitPoints, int minChange, int maxChange)
    {
        boolean increase = Math.random() > 0.5;
        int randomChange;

        if (increase)
        {
            randomChange = (int) (Math.random() * (maxChange + 1));
        }
        else
        {
            randomChange = (int) (Math.random() * (minChange + 1));
            randomChange = -randomChange;
        }

        int randHitPoints = hitPoints + randomChange;

        this.hitPoints = Math.max(randHitPoints, 1);

        if (this.hitPoints > maxHitPoints)
        {
            this.maxHitPoints = this.hitPoints;
        }
    }

    public int getDexterity() {return dexterity;}
    public void setDexterity(int dexterity) {this.dexterity = dexterity;}
    public int getIntellect() {return intellect;}
    public void setIntellect(int intellect) {this.intellect = intellect;}
    public int getStamina() {return stamina;}
    public void setStamina(int stamina) {this.stamina = stamina;}

    public EntityStatistics()
    {

    }
}
