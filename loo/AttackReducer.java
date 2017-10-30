package loo;

import statecontainer.Reducer;
import statecontainer.Action;
import statecontainer.State;
import java.util.HashMap;

public class AttackReducer implements Reducer {
  public State reduce(final State state, final Action action) {
    switch (action.getType()) {
      case Actions.ADD_HERO:
        Hero hero = (((InitGamePayload) action.getPayload()).hero);
        Point position = (((InitGamePayload) action.getPayload()).position);
        HashMap<Point, Hero> newHash = new HashMap<Point, Hero>(state.getHeroMap());
        System.out.println("Hereaaa");
        newHash.put(position, hero);
        return new GameState();
      default:
        return new GameState();
    }
  }
}
