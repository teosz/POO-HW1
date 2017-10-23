package statecontainer;

import java.util.HashMap;
class Spell {

}

class Hero {
  private String type;
  private int health;
  private int level;
  private Spell[] spells;
}

class State {
  private final HashMap<Point, Hero> heroMap;
  State() {
    this.heroMap = new HashMap<Point, Hero>();
  }

  State(HashMap<Point, Hero> heroMap) {
    this.heroMap = heroMap;
  }

  public HashMap<Point, Hero> getHeroMap() {
    return this.heroMap;
  }
}
