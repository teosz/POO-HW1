package loo;
import java.util.List;
import java.util.ArrayList;

public final class Hero {
  private String type;
  private int baseHP;
  private int levelupHP;
  private int currentHP;
  private float hp;
  private int level;
  private int experience;
  private List<Spell> spells;
  private List<Spell> hits;

  Hero() {
    this.hits = new ArrayList<Spell>();
    this.experience = 0;
    this.level = 0;
  }

  Hero(final String type, final int baseHP, final int levelupHP, List<Spell> spells) {
    this.type = type;
    this.baseHP = baseHP;
    this.currentHP = baseHP;
    this.levelupHP = levelupHP;
    this.hp = baseHP;
    this.spells = spells;
    this.experience = 0;
    this.level = 0;
  }

  public void hit(Spell spell, int demage) {
    this.hits.add(spell);
    this.currentHP -= demage;
  }

  public int getCurrentHP() {
    return this.currentHP;
  }

  public List<Spell> getHits() {
    return this.hits;
  }

  public int geHero() {
    return this.currentHP;
  }


  public int getBaseHP() {
    return this.baseHP;
  }

  public String getType() {
    return this.type;
  }

  public List<Spell> getSpells() {
    return this.spells;
  }

  public static Hero fromObject(Object hero) {
    return Hero.class.cast(hero);
  }

  public String toString() {
    return String.format("%s %d %d (exp:%d) (%d)", this.type, this.currentHP, this.levelupHP, this.experience, this.spells.size());

  }

}
