package loo;
import java.util.List;

public final class Hero {
  private String type;
  private int baseHP;
  private int levelupHP;
  private float hp;
  private int level;
  private int experience;
  private List<Spell> spells;
  Hero(final String type, final int baseHP, final int levelupHP, List<Spell> spells) {
    this.type = type;
    this.baseHP = baseHP;
    this.levelupHP = levelupHP;
    this.hp = baseHP;
    this.spells = spells;
    this.experience = 0;
    this.level = 0;
  }

  public List<Spell> getSpells() {
    return this.spells;
  }

  public static Hero fromObject(Object hero) {
    return Hero.class.cast(hero);
  }

  public String toString() {
    return String.format("%s %d %d (exp:%d) (%d)", this.type, this.baseHP, this.levelupHP, this.experience, this.spells.size());

  }

}
