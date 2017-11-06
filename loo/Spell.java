package loo;
import java.util.Map;

public final class Spell {
  private String name;
  private int baseDamage;
  private int levelupDamage;
  private Map options;
  private Map modifiers;
  private Map terrainModifier;

  public String getName() {
    return this.name;
  }

  public int getBaseDamage() {
    return this.baseDamage;
  }

  public Map getOptions() {
    return this.options;
  }

  public float getModifier(final Hero hero) {
    String key = hero.getType();
    if (this.modifiers.containsKey(key)) {
      double modifier = (double) this.modifiers.get(key);
      return (float) modifier / 100;
    }
    return 0;
  }

  public String toString() {
    return String.format("%s %d %d", this.name, this.baseDamage, this.levelupDamage);
  }

  public static Spell fromObject(final Object hero) {
    return Spell.class.cast(hero);
  }
}
