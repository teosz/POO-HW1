package loo;
import java.util.List;
import java.util.Map;


public class Spell {
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

  public float getModifier(Hero hero) {
    double modifier = (double) this.modifiers.get(hero.getType());
    return (float) modifier / 100;
  }

  public String toString() {
    return String.format("%s %d %d", this.name, this.baseDamage, this.levelupDamage);
  }

  public static Spell fromObject(Object hero) {
    return Spell.class.cast(hero);
  }
}
