package loo;

import statecontainer.State;
import java.util.HashMap;
class GameState implements State {
  private final HashMap<Point, Hero> heroMap;
  GameState() {
    this.heroMap = new HashMap<Point, Hero>();
  }

  GameState(final HashMap<Point, Hero> heroMap) {
    this.heroMap = heroMap;
  }

public HashMap<Point, Hero> getHeroMap() {
    return this.heroMap;
  }
}
