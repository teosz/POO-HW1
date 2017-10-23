package statecontainer;

import java.util.HashMap;
class Spell {

}
class Point {

}
class Hero {
  private String type;
  private int health;
  private int level;
  private Spell[] spells;
}

class State {
  private HashMap<Point, Hero> heroMap;
}
