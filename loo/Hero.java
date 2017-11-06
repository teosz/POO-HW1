package loo;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
  * Class providing the functionality to manange heros.
  */
public final class Hero {
  private static final int LEVEL_UP_LIMIT = 200;
  private static final int LEVEL_MULTIPLIER = 40;
  private static final int LEVEL_UP_EXPERINCE = 250;
  private static final int LEVEL_UP_EXPERINCE_MULTLIPLIER = 50;

  private String type;
  private int baseHP;
  private int levelupHP;
  private int currentHP;
  private float hp;
  private int level;
  private int experience;
  private int frozenRounds;
  private List<Spell> spells;
  private Map ability;
  private List<Integer> plainHits;
  private List<Integer> delayedHits;
  private Hero killer;

  /**
    * Initiate a new hero.
    * Initiate the fields which are not injected by GSON from game config.
    */
  Hero() {
    this.delayedHits = new ArrayList<Integer>();
    this.plainHits = new ArrayList<Integer>();
    this.experience = 0;
    this.level = 0;
    this.frozenRounds = 0;
  }


  /**
    * Cast a hero from Object.
    * @param hero object to be casted
    * @return object casted into a Hero
    */
  public static Hero fromObject(final Object hero) {
    return Hero.class.cast(hero);
  }

  /**
  * Immobilize the hero for a number of rounds.
  * @param rounds no. of rounds
  */
  public void freeze(final int rounds) {
    this.frozenRounds = rounds;
  }

  /**
  * Verifiy if is frozen.
  * @return true is the hero is frozen, false otherwise
  */
  public boolean isFrozen() {
    return (this.frozenRounds > 0);
  }

  /**
  * Decrese the number of frozen rounds.
  */
  public void decreseFrozenRounds() {
    this.frozenRounds--;
  }

  /**
  * Increase the experience of hero.
  * @param looserLevel the level from which the experience is deducted.
  */
  public void increaseXP(final int looserLevel) {
    this.experience += Math.max(
      0,
      LEVEL_UP_LIMIT - (this.getLevel() - looserLevel * LEVEL_MULTIPLIER)
    );
  }

  /**
  * Increase the current level if possible.
  */
  public void levelUP() {
    if (this.getLevel() * LEVEL_UP_EXPERINCE_MULTLIPLIER > LEVEL_UP_EXPERINCE) {
      this.baseHP += this.levelupHP;
      this.currentHP = this.baseHP;
      this.spells.stream().forEach(spell -> spell.levelUP());
      this.level++;
    }
  }

  /**
  * Hit the hero with damage.
  * @param author author of the hit.
  * @param damage damage of the hit.
  */
  public void hit(final Hero author, final int damage) {
    this.currentHP -= damage;
    if (this.isDead()) {
      this.killer = author;
    }
  }

  /**
  * Hit the hero with damage and plain damage.
  * @param author author of the hit.
  * @param damage damage of the hit.
  * @param plainDamage plain damage which will be returned in deflect abilities.
  */
  public void hit(final Hero author, final int damage, final int plainDamage) {
    this.plainHits.add(plainDamage);
    this.hit(author, damage);
  }

  /**
  * Anonymous hit the hero with damage.
  * @param damage damage of the hit.
  */
  public void hit(final int damage) {
    this.currentHP -= damage;
  }

  /**
  * Check if the hero has a killler.
  * @return true if killer exists false otherwise
  */
  public boolean hasKiller() {
    return this.killer != null;
  }


  /**
  * Remove the killer of current hero.
  */
  public void removeKiller() {
    this.killer = null;
  }

  /**
  * Clean the list of the plain hits.
  */
  public void clearPlainHits() {
    this.plainHits.clear();
  }

  /**
  * Clean the list of the delayed hits.
  */
  public void cleanDelayedHits() {
    this.delayedHits.clear();
  }

  /**
  * Hit with damage which will be aplied in future round.
  * @param damage damage of hit
  */
  public void hitWithDelay(final int damage) {
    this.delayedHits.add(damage);
  }

  /**
  * get hits which will be applied in future.
  * @return delayed hits
  */
  public List<Integer> getDelayedHits() {
    return this.delayedHits;
  }


  /**
  * pop first deleyed hit.
  * @return the element removed from delayed hits.
  */
  public int popDelayedHit() {
    return this.delayedHits.remove(0);
  }

  /**
  * get the terrian type of hero ability.
  * @return String symbolizing the terrain.
  */
  public String getAbilityTerrain() {
    return this.ability.get("terrain").toString();
  }

  /**
  * get the modifier of the terrian.
  * @return float symbolizing the modifier of the terrain.
  */
  public float getAbilityModifier() {
    double modifier = (double) this.ability.get("modifier");
    return (float) modifier / Constants.FROM_PROCENT;
  }

  /**
  * @return the level of the hero.
  */
  public int getLevel() {
    return this.level;
  }

  /**
  * @return the hp of the hero.
  */
  public int getCurrentHP() {
    return this.currentHP;
  }

  /**
  * @return list of plain hits applied to the hero.
  */
  public List<Integer> getPlainHits() {
    return this.plainHits;
  }

  /**
  * @return the base(initial) hp of the hero
  */
  public int getBaseHP() {
    return this.baseHP;
  }

  /**
  * @return the killer of the hero
  */
  public Hero getKiller() {
    return this.killer;
  }

  /**
  * @return the type of the hero
  */
  public String getType() {
    return this.type;
  }


  /**
  * @return all the spell of the hero
  */
  public List<Spell> getSpells() {
    return this.spells;
  }


  /**
  * Check if is dead.
  * @return true if dead false otherwise
  */
  public boolean isDead() {
    return (this.currentHP <= 0);
  }

  public String toString() {
    Character symbol = Symbols.getHeroSymbolFromType(this.type);
    if (this.isDead()) {
      return String.format("%c dead", symbol);
    }
    return String.format("%c %d %d %d", symbol, this.level, this.experience, this.currentHP);
  }

}
