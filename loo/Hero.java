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
  private List<Hero> delayedAuthors;
  private Hero killer;

  Hero() {
    this.delayedHits = new ArrayList<Integer>();
    this.delayedAuthors = new ArrayList<Hero>();
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

  public boolean isFrozen() {
    return (this.frozenRounds > 0);
  }

  public void decreseFrozenRounds() {
    this.frozenRounds--;
  }

  public void increaseXP(int looserLevel) {
    System.out.println("here");
    this.experience += Math.max(0, 200 - (this.getLevel() - looserLevel * 40));
  }

  public void levelUP() {
    // if(250 + this.get) {
    //
    // }
  }

  public void hit(Hero author, int damage, int plainDamage) {
    this.plainHits.add(plainDamage);
    this.currentHP -= damage;
    if(this.isDead())
      this.killer = author;
  }

  public void hit(Hero author, int damage) {
    this.currentHP -= damage;
    if(this.isDead())
      this.killer = author;
  }

  public void clearPlainHits() {
    this.plainHits.clear();
  }


  public void hitWithDelay(Hero author, int damage) {
    this.delayedHits.add(damage);
    this.delayedAuthors.add(author);
  }

  public List<Integer> getDelayedHits() {
    return this.delayedHits;
  }

  public int popDelayedHit() {
    return this.delayedHits.remove(0);
  }

  public Hero popDelayedAuthor() {
   return this.delayedAuthors.remove(0);
  }

  public int getLevel() {
    return this.level;
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

  public Hero getKiller() {
    return this.killer;
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
