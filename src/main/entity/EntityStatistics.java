package main.entity;

public class EntityStatistics
{
    Entity entity;

    public int maxHitPoints = 1;                // maximum hit points
    public int hitPoints = maxHitPoints;        // current hit points - if 0 or lower - entity dies
    protected int regeneration;                 // regeneration of health per second

    public int maxMana = 1;                     // current mana - needed to cast spells or hold specific items
    public int mana = maxMana;                  // max mana - how much spells you can cast at short amount of time

    protected int movementSpeed;                // how fost entity travels through map

    protected int armour;                       // how resistant entity is against physical damage
    protected int magicArmour;                  // how resistant entity is against magical damage

    protected int strength;                     // statistic exclusive for melee weapons - how strong you hit
    protected int dexterity;                    // statistic exclusive for bows and actions needing dexterity - how powerful you can shoot
    protected int intellect;                    // statistic exclusive for magic - how strong your spells are

    protected int stamina;                      // statistic used in blocking attacks and fighting melee weapons. Lower stamina = lower damage and weaker ability to block

    public int getMaxHitPoints() {return maxHitPoints;}
    public void setMaxHitPoints(int maxHitPoints) {this.maxHitPoints = maxHitPoints;}
    public int getHitPoints() {return hitPoints;}
    public void setHitPoints(int hitPoints) {this.hitPoints = hitPoints;}
    public int getRegeneration() {return regeneration;}
    public void setRegeneration(int regeneration) {this.regeneration = regeneration;}
    public int getMaxMana() {return maxMana;}
    public void setMaxMana(int maxMana) {this.maxMana = maxMana;}
    public int getMana() {return mana;}
    public void setMana(int mana) {this.mana = mana;}
    public int getMovementSpeed() {return movementSpeed;}
    public void setMovementSpeed(int movementSpeed) {this.movementSpeed = movementSpeed;}
    public int getArmour() {return armour;}
    public void setArmour(int armour) {this.armour = armour;}
    public int getMagicArmour() {return magicArmour;}
    public void setMagicArmour(int magicArmour) {this.magicArmour = magicArmour;}
    public int getStrength() {return strength;}
    public void setStrength(int strength) {this.strength = strength;}
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
