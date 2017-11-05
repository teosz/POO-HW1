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
  private int frozenRounds;
  private List<Spell> spells;
  private List<Integer> plainHits;
  private List<Integer> delayedHits;

  Hero() {
    this.delayedHits = new ArrayList<Integer>();
    this.plainHits = new ArrayList<Integer>();
    this.experience = 0;
    this.level = 0;
    this.frozenRounds = 0;
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

  public static Hero fromObject(Object hero) {
    return Hero.class.cast(hero);
  }

  public void freeze(int rounds) {
    this.frozenRounds = rounds;
  }

  public void hit(int damage, int plainDamage) {
    this.plainHits.add(plainDamage);
    this.currentHP -= damage;
  }

  public void clearPlainHits() {
    this.plainHits.clear();
  }

  public void hit(int damage) {
    this.currentHP -= damage;
  }

  public void hitWithDelay(int damage) {
    this.delayedHits.add(damage);
  }

  public List<Integer> getDelayedHits() {
    return this.delayedHits;
  }

  public int popDelayedHit() {
    return this.delayedHits.remove(0);
  }

  public int getCurrentHP() {
    return this.currentHP;
  }

  public List<Integer> getPlainHits() {
    return this.plainHits;
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

  public boolean isDead() {
    return (this.currentHP <= 0);
  }
  public String toString() {
    Character symbol = HeroFactory.getSymbolFromType(this.type);
    if(this.isDead())
      return String.format("%c dead", symbol);
    return String.format("%c %d %d %d", symbol, this.level, this.experience, this.currentHP);
  }

}
