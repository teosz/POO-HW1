package loo;

public class Hero {
  private String type;
  private int baseHP;
  private int levelupHP;
  private float hp;
  private int level;
  private int experience;
  private Spell[] spells;
  Hero(String type, int baseHP, int levelupHP) {
    this.type = type;
    this.baseHP = baseHP;
    this.levelupHP = levelupHP;
    this.hp = baseHP;
    this.experience = 0;
    this.level = 0;
    this.spells = new Spell[0];
  }

  public String toString() {
    return String.format("%s exp %d", this.type, this.experience);
  }

}

class Spell {
  public void attack() {

  }
}
