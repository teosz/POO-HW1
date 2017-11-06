package loo;
import java.util.Map;
/**
  * Class providing the functionality to manage Spells.
  */
public final class Spell {
  private String name;
  private int baseDamage;
  private int levelupDamage;
  private Map options;
  private Map modifiers;
  private Map terrainModifier;

  /**
    * @return the name of the spell
    */
  public String getName() {
    return this.name;
  }

  /**
    * @return the damage of the spell
    */
  public int getBaseDamage() {
    return this.baseDamage;
  }

  /**
    * @return spell options
    */
  public Map getOptions() {
    return this.options;
  }

  /**
    * Get the damage modifier when the spell is applied to other hero.
    * @param hero the hero which is attacked
    * @return modifier modifier of the damage
    */
  public float getModifier(final Hero hero) {
    String key = hero.getType();
    if (this.modifiers.containsKey(key)) {
      double modifier = (double) this.modifiers.get(key);
      return (float) modifier / Constants.FROM_PROCENT;
    }
    return 0;
  }

  /**
    * Increase the damage on levelUP.
    */
  public void levelUP() {
    this.baseDamage += this.levelupDamage;
  }

  /**
    * Cast a spell from Object.
    * @param spell object to be casted
    * @return object casted into a Spell
    */
  public static Spell fromObject(final Object spell) {
    return Spell.class.cast(spell);
  }

  public String toString() {
    return String.format("%s %d %d", this.name, this.baseDamage, this.levelupDamage);
  }
}
